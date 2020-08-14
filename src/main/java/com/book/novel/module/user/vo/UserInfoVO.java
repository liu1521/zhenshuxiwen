package com.book.novel.module.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/13
 * @Description: 用户信息VO
 */

@Data
public class UserInfoVO {

    @NotNull(message = "主键id不可为空")
    @ApiModelProperty("主键id")
    private Integer id;

    @NotNull(message = "用户名不可为空")
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("头像url")
    private String headImgUrl;

    @ApiModelProperty("用户简介")
    private String introduce;

}
