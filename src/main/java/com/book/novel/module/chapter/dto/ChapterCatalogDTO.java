package com.book.novel.module.chapter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 章节信息DTO
 */

@Data
public class ChapterCatalogDTO {

    private Integer id;

    private String title;

    private Date updateTime;

    private Integer chapterNumber;
}
