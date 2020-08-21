package com.book.novel.module.chapter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/20
 * @Description: 章节上传VO
 */

@Data
public class ChapterUploadVO {

    @NotNull
    @ApiModelProperty("所属小说id")
    private Integer novelId;

    @NotNull
    @ApiModelProperty("章节正文")
    private String content;

    @NotNull
    @ApiModelProperty("章节标题")
    private String title;

}
