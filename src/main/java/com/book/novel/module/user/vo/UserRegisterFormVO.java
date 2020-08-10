package com.book.novel.module.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户注册VO
 */

@Data
public class UserRegisterFormVO implements Serializable {

    @NotNull(message = "登陆名不能为空")
    @ApiModelProperty(example = "admin")
    private String registerUsername;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(example = "123456")
    private String registerPwd;

    @NotNull(message = "邮箱不能为空")
    @ApiModelProperty(example = "1111@qq.com")
    private String email;

    @NotNull(message = "验证码id不能为空")
    @ApiModelProperty(value = "验证码uuid")
    private String codeUuid;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "男")
    private String sex;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

}
