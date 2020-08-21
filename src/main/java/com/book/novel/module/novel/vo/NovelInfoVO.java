package com.book.novel.module.novel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/18
 * @Description: 小说基本信息VO
 */

@Data
public class NovelInfoVO {

    @NotNull(message = "小说id不能为空")
    @ApiModelProperty("小说id")
    private Integer novelId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("小说类型id")
    private Integer categoryId;

    @ApiModelProperty("小说封面图片路径")
    private String pic;

    @ApiModelProperty("小说简介")
    private String introduce;

    @ApiModelProperty("小说标题")
    private String title;



}
