package com.book.novel.module.user;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户相关路由
 */

@Api("用户操作相关接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/testUsername")
    @ApiOperation(value = "验证用户名", notes = "验证用户名是否重复")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> testUsername(String username) {
        return userService.testUsername(username);
    }

    @GetMapping("/api/user/active")
    @ApiOperation(value = "激活账号", notes = "激活链接5分钟内有效")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> active(String mailUuid) {
        return userService.active(mailUuid);
    }

}
