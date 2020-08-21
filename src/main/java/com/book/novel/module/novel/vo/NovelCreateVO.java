package com.book.novel.module.novel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/18
 * @Description: 创建小说VO
 */

@Data
public class NovelCreateVO {

    @NotNull(message = "小说标题不能为空")
    @ApiModelProperty("小说标题")
    private String title;

    @ApiModelProperty("小说封面图片路径")
    private String pic;

    @ApiModelProperty("小说简介")
    private String introduce;

    @NotNull(message = "小说类型id不能为空")
    @ApiModelProperty("小说类型id")
    private Integer categoryId;

}
