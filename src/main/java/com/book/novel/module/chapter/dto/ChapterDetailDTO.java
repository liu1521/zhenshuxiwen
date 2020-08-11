package com.book.novel.module.chapter.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 章节详情DTO
 */

@Data
public class ChapterDetailDTO {

    private Integer id;

    private String novelTitle;

    private Integer novelId;

    private String author;

    private String content;

    private String title;

    private Date updateTime;

}
