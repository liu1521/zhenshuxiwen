package com.book.novel.module.login;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.module.mail.MailService;
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
import com.book.novel.module.user.vo.UserRegisterFormVO;
import com.book.novel.util.BeanUtil;
import com.book.novel.util.JsonUtil;
import com.book.novel.util.Md5Util;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

    public static final String WAIT_ACTIVE_USER_PREFIX = "wait_active";

    public static final String ACTIVE_EMAIL_ADDR = "118.31.229.85";

    @Autowired
    private UserService userService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    public ResponseDTO<LoginDetailDTO> login(@Valid UserLoginFormVO userLoginFormVO, HttpServletRequest request) {
        String redisVerificationCodeUuid = getVerificationCodeFromRedis(userLoginFormVO.getCodeUuid());
        if (StringUtils.isEmpty(redisVerificationCodeUuid)) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }
        if (! redisVerificationCodeUuid.equalsIgnoreCase(userLoginFormVO.getCode())) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }

        String encryptPassword = Md5Util.encryptPassword(userLoginFormVO.getLoginPwd(), userLoginFormVO.getLoginName());

        UserEntity userEntity = userService.getUserByUsernameAndPassword(userLoginFormVO.getLoginName(), encryptPassword);
        if (userEntity == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_FAILED);
        }

        if (UserStatusEnum.NOT_ACTIVE.getValue().equals(userEntity.getStatus())) {
            return ResponseDTO.wrap(UserResponseCodeConst.NO_ACTIVE);
        }

        if (UserStatusEnum.DISABLED.getValue().equals(userEntity.getStatus())) {
            return ResponseDTO.wrap(UserResponseCodeConst.IS_DISABLED);
        }

        String requestIp = request.getRemoteAddr();
        userEntity.setLastLoginIp(requestIp);
        userEntity.setLoginCount(userEntity.getLoginCount()+1);
        userEntity.setLastLoginTime(new Date());
        userService.updateUserLoginInfo(userEntity);


        LoginDetailDTO loginDetailDTO = BeanUtil.copy(userEntity, LoginDetailDTO.class);

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

        // 设置状态
        loginDetailDTO.setStatus(UserStatusEnum.NORMAL.getDesc());

        // 设置经验
        loginDetailDTO.setExp(userEntity.getExp()%30);

        // 设置等级
        loginDetailDTO.setLevel(userEntity.getExp()/30 + 1);

        String token = loginTokenService.generateToken(loginDetailDTO);
        loginDetailDTO.setXAccessToken(token);

        return ResponseDTO.succData(loginDetailDTO);
    }

    public ResponseDTO<ResponseCodeConst> register(UserRegisterFormVO userRegisterFormVO) {
        String redisVerificationCode = getVerificationCodeFromRedis(userRegisterFormVO.getCodeUuid());
        if (StringUtils.isEmpty(redisVerificationCode)) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }
        if (! redisVerificationCode.equalsIgnoreCase(userRegisterFormVO.getCode())) {
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_INVALID);
        }

        // 检测用户名是否存在于db
        String registerUsername = userRegisterFormVO.getUsername();
        Integer id = userService.getIdByUsername(registerUsername);
        if (id != null) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
        }

        // 检测邮箱是否存在于db
        String registerEmail = userRegisterFormVO.getEmail();
        id = userService.getIdByEmail(registerEmail);
        if (id != null) {
            return ResponseDTO.wrap(UserResponseCodeConst.EMAIL_EXISTS);
        }

        // 检测用户名或邮箱是否存在于待激活用户中
        Set<String> waitActiveUserKey = redisValueOperations.getOperations().keys(WAIT_ACTIVE_USER_PREFIX);
        if (CollectionUtils.isNotEmpty(waitActiveUserKey)) {
            List<String> waitActiveUserString = redisValueOperations.multiGet(waitActiveUserKey);
            if (CollectionUtils.isNotEmpty(waitActiveUserString)) {
                for (String json : waitActiveUserString) {
                    UserRegisterFormVO waitActiveUser = (UserRegisterFormVO) JsonUtil.toObject(json, UserRegisterFormVO.class);

                    if (userRegisterFormVO.getUsername().equals(waitActiveUser.getUsername())) {
                        return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
                    }
                    if (userRegisterFormVO.getEmail().equals(waitActiveUser.getEmail())) {
                        return ResponseDTO.wrap(UserResponseCodeConst.EMAIL_EXISTS);
                    }
                }
            }
        }

        // 发送激活邮件
        String mailUuid = UUID.randomUUID().toString();
        String subject = "枕书席文";
        String content = "<h1>欢迎使用枕书席文,点击下方链接激活账号</h1>" +
                "<a href='http://"+ACTIVE_EMAIL_ADDR+"/api/user/active?mailUuid="+mailUuid+"'>激活</a>";
        mailService.sendHtmlMail(userRegisterFormVO.getEmail(), subject, content);

        // 将注册用户信息存入redis 过期时间5分钟
        String json = JsonUtil.toJson(userRegisterFormVO);
        if (StringUtils.isEmpty(json)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ERROR_PARAM);
        }
        redisValueOperations.set(WAIT_ACTIVE_USER_PREFIX+mailUuid, json, 300L, TimeUnit.SECONDS);

        return ResponseDTO.wrap(UserResponseCodeConst.REGISTER_SUCCESS);
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

    private String getVerificationCodeFromRedis(String key) {
        String verificationCode = redisValueOperations.get(key);
        redisValueOperations.getOperations().delete(key);
        return verificationCode;
    }

}
