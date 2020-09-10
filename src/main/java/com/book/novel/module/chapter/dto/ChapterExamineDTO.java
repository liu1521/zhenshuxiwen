package com.book.novel.module.chapter.dto;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/9/10
 * @Description:
 */

@Data
public class ChapterExamineDTO {

    private Integer id;

    private String novelTitle;

    private Integer novelId;

    private String author;

    private String content;

    private String title;

}
