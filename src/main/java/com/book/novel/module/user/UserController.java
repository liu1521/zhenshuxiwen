package com.book.novel.module.user;

import com.book.novel.common.anno.NeedAdmin;
import com.book.novel.common.anno.NeedUser;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.constant.UserStatusEnum;
import com.book.novel.module.user.dto.AddressDTO;
import com.book.novel.module.user.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "验证用户名", notes = "验证用户名是否重复")
    @PostMapping("/api/user/username/test")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> testUsername(@RequestParam String username) {
        return userService.testUsername(username);
    }

    @ApiOperation(value = "上传头像", notes = "图片格式.jpg .jpeg .png;修改个人信息提交的时候先上传头像拿到返回头像url,将头像url写入个人信息的一个input标签,并将所有的个人信息传到服务器")
    @PostMapping("/api/user/headImg/upload")
    @NoNeedLogin
    public ResponseDTO uploadHeadImg(@RequestParam("avatar") MultipartFile multipartFile) {
        return userService.uploadHeadImg(multipartFile);
    }

    @ApiOperation(value = "修改个人信息", notes = "修改用户名、性别、头像、简介,其中头像填 /api/user/headImg/upload返回的url")
    @PostMapping("/api/user/info/update")
    @NeedUser
    public ResponseDTO<LoginDetailDTO> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        return userService.updateUserInfo(userInfoVO);
    }

    @ApiOperation(value = "收藏/取消收藏 小说")
    @PostMapping("/api/user/favorites")
    @NeedUser
    public ResponseDTO favorites(@RequestParam Integer novelId, HttpServletRequest request) {
        return userService.favorites(novelId, request);
    }

    @ApiOperation(value = "投推荐票(exp+5)")
    @PostMapping("/api/user/recommend")
    @NeedUser
    public ResponseDTO recommend(@RequestParam Integer novelId, HttpServletRequest request) {
        return userService.recommend(novelId, request);
    }

    @ApiOperation(value = "获取已收藏小说")
    @GetMapping("/api/user/favorites/get")
    @NeedUser
    public ResponseDTO<List<NovelDTO>> listFavoritesNovel(HttpServletRequest request) {
        return userService.listFavoritesNovel(request);
    }

    @ApiOperation(value = "注册成为作家，需要等待管理员审核")
    @GetMapping("/api/user/author/register")
    @NeedUser
    public ResponseDTO register2author(HttpServletRequest request) {
        return userService.updateUserStatus(UserStatusEnum.TO_BE_AUTHOR.getValue(), request);
    }

    @ApiOperation(value = "激活账号", notes = "激活链接5分钟内有效")
    @GetMapping("/api/user/active")
    @NoNeedLogin
    public ResponseDTO<ResponseCodeConst> active(@RequestParam String mailUuid) {
        return userService.active(mailUuid);
    }

    @ApiOperation(value = "获取想要注册成为作家的所有用户")
    @GetMapping("/api/admin/registerToAuthor/get")
    @NeedAdmin
    public ResponseDTO<PageResultDTO<UserBO>> listRegisterToAuthorUser(@Valid @RequestBody PageParamDTO pageParamDTO) {
        return userService.listRegisterToAuthorUser(pageParamDTO);
    }

    @ApiOperation(value = "注册成为作者审核通过")
    @PostMapping("/api/admin/registerToAuthor/success")
    @NeedAdmin
    public ResponseDTO registerToAuthorSuccess(@RequestParam Integer userId) {
        return userService.registerToAuthorExamine(userId, true);
    }

    @ApiOperation(value = "注册成为作者审核拒绝")
    @PostMapping("/api/admin/registerToAuthor/fail")
    @NeedAdmin
    public ResponseDTO registerToAuthorFail(@RequestParam Integer userId) {
        return userService.registerToAuthorExamine(userId, false);
    }

    @ApiOperation(value = "封号")
    @PostMapping("/api/admin/user/kick")
    @NeedAdmin
    public ResponseDTO kickUser(@RequestParam Integer userId) {
        return userService.kickUser(userId, true);
    }

    @ApiOperation(value = "解封账号")
    @PostMapping("/api/admin/user/unkick")
    @NeedAdmin
    public ResponseDTO unKickUser(@RequestParam Integer userId) {
        return userService.kickUser(userId, false);
    }

    @ApiOperation(value = "获取当前用户收件信息")
    @GetMapping("/api/user/info/address")
    @NeedUser
    public ResponseDTO<List<AddressDTO>> listAddress(HttpServletRequest request) {
        return ResponseDTO.succData(userService.listAddress(request));
    }

    @ApiOperation(value = "修改当前用户收件信息")
    @PostMapping("/api/user/info/address/{uid}")
    @NoNeedLogin
    public ResponseDTO updateAddress(@PathVariable Integer uid, HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
        userService.updateAddress(uid, request, addressDTO);
        return ResponseDTO.succ();
    }

//    @ApiOperation(value = "删除当前用户指定收件信息")
//    @PostMapping("/api/user/info/address/{addressId}")
//    @NoNeedLogin
//    public ResponseDTO deleteAddress(@PathVariable Integer addressId) {
//        boolean deleted = userService.deleteAddress(addressId);
//        if (deleted) {
//            return ResponseDTO.succ();
//        } else {
//            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
//        }
//    }

    @ApiOperation(value = "修改默认收件地址")
    @PostMapping("/api/user/info/address/default/{addressId}/{uid}")
    @NoNeedLogin
    public ResponseDTO updateDefaultAddress(@PathVariable Integer addressId, @PathVariable Integer uid) {
        userService.updateDefaultAddress(addressId, uid);
        return ResponseDTO.succ();
    }
}
