package com.book.novel.module.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户登陆表单VO
 */

@ApiModel(value = "UserLoginFormVO对象")
@Data
public class UserLoginFormVO {

    @NotNull(message = "登陆名不能为空")
    @ApiModelProperty(example = "1111@qq.com")
    private String loginName;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(example = "123456")
    private String loginPwd;

    @NotNull(message = "验证码id不能为空")
    @ApiModelProperty(value = "验证码uuid")
    private String codeUuid;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;
}
