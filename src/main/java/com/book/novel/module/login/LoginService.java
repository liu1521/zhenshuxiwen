package com.book.novel.module.login;

import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.KaptchaDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.vo.UserLoginFormVO;
import com.book.novel.module.user.UserService;
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

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public ResponseDTO<LoginDetailDTO> login(@Valid UserLoginFormVO userLoginFormVO) {
//        String redisVerificationCodeUuid = redisValueOperations.get(userLoginFormVO.getCodeUuid());
//        redisValueOperations.getOperations().delete(userLoginFormVO.getCodeUuid());
//        if (StringUtils.isEmpty(redisVerificationCodeUuid)) {
//            return ResponseDTO.wrap(UserCodeConst.VERIFICATION_CODE_INVALID);
//        }
//        if (! redisVerificationCodeUuid.equalsIgnoreCase(userLoginFormVO.getCode())) {
//            return ResponseDTO.wrap(UserCodeConst.VERIFICATION_CODE_INVALID);
//        }
        String encryptPassword = ShrioUtil.encryptPassword(userLoginFormVO.getLoginPwd(), userLoginFormVO.getLoginName());

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginFormVO.getLoginName(), encryptPassword);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResponseDTO.wrap(UserResponseCodeConst.USER_NOT_EXISTS);
        } catch (IncorrectCredentialsException e) {
            return ResponseDTO.wrap(UserResponseCodeConst.PASSWORD_ERROR);
        }

        return null;
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

    public LoginDetailDTO getLoginDetailDTOByUsername(String username) {
        return userService.getLoginDetailDTOByUsername(username);
    }
}
