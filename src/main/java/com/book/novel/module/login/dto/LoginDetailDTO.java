package com.book.novel.module.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 登陆详情DTO
 */

@Data
public class LoginDetailDTO {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("头像url")
    private String headImgUrl;

    @ApiModelProperty("简介")
    private String introduce;

    @ApiModelProperty("账号状态")
    private String status;

    @ApiModelProperty("经验")
    private Integer exp;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("推荐票数量")
    private Integer recommend;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("xAccessToken")
    private String xAccessToken;
}
