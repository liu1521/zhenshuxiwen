package com.book.novel.module.user;

import com.book.novel.common.anno.NeedUser;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.user.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户相关路由
 */

@Api(tags = "用户操作相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header",dataType = "string",dataTypeClass = String.class)})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/username/test")
    @ApiOperation(value = "验证用户名", notes = "验证用户名是否重复")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> testUsername(@RequestParam String username) {
        return userService.testUsername(username);
    }

    @GetMapping("/api/user/active")
    @ApiOperation(value = "激活账号", notes = "激活链接5分钟内有效")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> active(@RequestParam String mailUuid) {
        return userService.active(mailUuid);
    }

    @PostMapping("/api/user/headImg/upload")
    @ApiOperation(value = "上传头像", notes = "图片格式.jpg .jpeg .png;修改个人信息提交的时候先上传头像拿到返回头像url,将头像url写入个人信息的一个input标签,并将所有的个人信息传到服务器")
    @NeedUser
    public ResponseDTO uploadHeadImg(@RequestParam MultipartFile multipartFile) {
        return userService.uploadHeadImg(multipartFile);
    }

    @PostMapping("/api/user/info/update")
    @ApiOperation(value = "修改个人信息", notes = "修改用户名、性别、头像、简介")
    @NeedUser
    public ResponseDTO<LoginDetailDTO> updateUserInfo(@Valid @RequestParam UserInfoVO userInfoVO) {
        return userService.updateUserInfo(userInfoVO);
    }

    @GetMapping("/api/user/favorites/get")
    @ApiOperation(value = "获取已收藏小说")
    @NeedUser
    public ResponseDTO<List<NovelDTO>> getFavoritesNovel(HttpServletRequest request) {
        return userService.getFavoritesNovel(request);
    }

}
