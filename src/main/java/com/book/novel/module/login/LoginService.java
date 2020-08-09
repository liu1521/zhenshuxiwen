package com.book.novel.module.login;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.module.role.constant.RoleEnum;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.KaptchaDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.constant.UserSexEnum;
import com.book.novel.module.user.constant.UserStatusEnum;
import com.book.novel.module.user.entity.UserEntity;
import com.book.novel.module.user.vo.UserLoginFormVO;
import com.book.novel.module.user.UserService;
import com.book.novel.util.BeanUtil;
import com.book.novel.util.ShrioUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 登陆相关业务
 */

@Slf4j
@Service
public class LoginService {

    private static final String VERIFICATION_CODE_REDIS_PREFIX = "vc_%s";

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    public ResponseDTO<LoginDetailDTO> login(@Valid UserLoginFormVO userLoginFormVO, HttpServletRequest request) {
        String redisVerificationCodeUuid = redisValueOperations.get(userLoginFormVO.getCodeUuid());
        redisValueOperations.getOperations().delete(userLoginFormVO.getCodeUuid());
        if (StringUtils.isEmpty(redisVerificationCodeUuid)) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }
        if (! redisVerificationCodeUuid.equalsIgnoreCase(userLoginFormVO.getCode())) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }

        String encryptPassword = ShrioUtil.encryptPassword(userLoginFormVO.getLoginPwd(), userLoginFormVO.getLoginName());
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginFormVO.getLoginName(), encryptPassword);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResponseDTO.wrap(UserResponseCodeConst.USER_NOT_EXISTS);
        } catch (IncorrectCredentialsException e) {
            return ResponseDTO.wrap(UserResponseCodeConst.PASSWORD_ERROR);
        }

        UserEntity userEntity = userService.getUserByUsername(token.getUsername());
        String requestIp = request.getRemoteAddr();
        userEntity.setLastLoginIp(requestIp);
        userEntity.setLoginCount(userEntity.getLoginCount()+1);
        userEntity.setLastLoginTime(new Date());
        userService.updateUserLoginInfo(userEntity);


        LoginDetailDTO loginDetailDTO = BeanUtil.copy(userEntity, LoginDetailDTO.class);

        // 设置状态
        if (UserStatusEnum.NORMAL.getValue().equals(userEntity.getStatus())) {
            loginDetailDTO.setStatus(UserStatusEnum.NORMAL.getDesc());
        } else if (UserStatusEnum.NOT_ACTIVE.getValue().equals(userEntity.getStatus())) {
            loginDetailDTO.setStatus(UserStatusEnum.NOT_ACTIVE.getDesc());
        } else if (UserStatusEnum.DISABLED.getValue().equals(userEntity.getStatus())) {
            loginDetailDTO.setStatus(UserStatusEnum.DISABLED.getDesc());
        }

        // 设置性别
        if (UserSexEnum.UNKNOWN.getValue().equals(userEntity.getSex())) {
            loginDetailDTO.setSex(UserSexEnum.UNKNOWN.getDesc());
        } else if (UserSexEnum.MALE.getValue().equals(userEntity.getSex())) {
            loginDetailDTO.setSex(UserSexEnum.MALE.getDesc());
        } else if (UserSexEnum.FEMALE.getValue().equals(userEntity.getSex())) {
            loginDetailDTO.setSex(UserSexEnum.FEMALE.getDesc());
        }

        // 设置角色
        if (RoleEnum.USER.getValue().equals(userEntity.getRoleId())) {
            loginDetailDTO.setRole(RoleEnum.USER.getDesc());
        } else if (RoleEnum.AUTHOR.getValue().equals(userEntity.getRoleId())) {
            loginDetailDTO.setRole(RoleEnum.AUTHOR.getDesc());
        } else if (RoleEnum.ADMIN.getValue().equals(userEntity.getRoleId())) {
            loginDetailDTO.setRole(RoleEnum.ADMIN.getDesc());
        }

        // 设置头像url
//        loginDetailDTO.setHeadImgUrl(FILE_URL_FIX+userEntity.getHeadImgUrl());

        // 设置经验
        loginDetailDTO.setExp(userEntity.getExp()%30);

        // 设置等级
        loginDetailDTO.setLevel(userEntity.getExp()/30 + 1);

        return ResponseDTO.succData(loginDetailDTO);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public ResponseDTO<KaptchaDTO> verificationCode() {
        KaptchaDTO kaptchaVO = new KaptchaDTO();
        String uuid = buildVerificationCodeRedisKey(UUID.randomUUID().toString());
        String kaptchaText = defaultKaptcha.createText();

        String base64Code = "";

        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("verificationCode exception .{}", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("verificationCode outputStream close exception .{}", e);
                }
            }
        }
        kaptchaVO.setUuid(uuid);
        kaptchaVO.setCode("data:image/png;base64," + base64Code);
        redisValueOperations.set(uuid, kaptchaText, 180L, TimeUnit.SECONDS);
        return ResponseDTO.succData(kaptchaVO);
    }

    private String buildVerificationCodeRedisKey(String uuid) {
        return String.format(VERIFICATION_CODE_REDIS_PREFIX, uuid);
    }

    public ResponseDTO<ResponseCodeConst> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseDTO.wrap(UserResponseCodeConst.LOGOUT_SUCCESS);
    }
}
