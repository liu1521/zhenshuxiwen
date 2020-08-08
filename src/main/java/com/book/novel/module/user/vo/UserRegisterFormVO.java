package com.book.novel.module.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户注册VO
 */

@Data
public class UserRegisterFormVO {

    @NotNull(message = "登陆名不能为空")
    @ApiModelProperty(example = "admin")
    private String registerUsername;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(example = "123456")
    private String registerPwd;

    @NotNull(message = "邮箱不能为空")
    @ApiModelProperty(example = "1111@qq.com")
    private String email;

//    @NotNull(message = "验证码id不能为空")
//    @ApiModelProperty(value = "验证码uuid")
//    private String codeUuid;
//
//    @NotNull(message = "验证码不能为空")
//    @ApiModelProperty(value = "验证码")
//    private String code;

}
