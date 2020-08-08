package com.book.novel.module.login;

import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.KaptchaDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.vo.UserLoginFormVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 登陆相关路由
 */

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/session/login")
    public ResponseDTO<LoginDetailDTO> login(@Valid UserLoginFormVO userLoginFormVO) {
        return loginService.login(userLoginFormVO);
    }

    @GetMapping("/session/verificationCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public ResponseDTO<KaptchaDTO> verificationCode() {
        return loginService.verificationCode();
    }

}
