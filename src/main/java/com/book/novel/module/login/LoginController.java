package com.book.novel.module.login;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.KaptchaDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.user.vo.UserLoginFormVO;
import com.book.novel.module.user.vo.UserRegisterFormVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 登陆相关路由
 */

@Api(tags = "用户登陆相关接口")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login")
    @ApiOperation(value = "登陆", notes = "登陆")
    @NoNeedLogin
    public ResponseDTO<LoginDetailDTO> login(@Valid @RequestBody UserLoginFormVO userLoginFormVO, HttpServletRequest request) {
        return loginService.login(userLoginFormVO, request);
    }

    @GetMapping("/api/verificationCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @NoNeedLogin
    public ResponseDTO<KaptchaDTO> verificationCode() {
        return loginService.verificationCode();
    }

    @PostMapping("/api/register")
    @ApiOperation(value = "注册", notes = "注册账号")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> register(@Valid @RequestBody UserRegisterFormVO userRegisterFormVO) {
        return loginService.register(userRegisterFormVO);
    }

}
