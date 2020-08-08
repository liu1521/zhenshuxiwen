package com.book.novel.module.chapter.entity;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description:
 */
public class ChapterEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 小说id
     */
    private Integer novelId;

    /**
     * 正文内容
     */
    private String content;

    /**
     * 审核状态  0未审核 1审核通过 2审核未通过
     */
    private Integer status;

    /**
     * 标题
     */
    private String title;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 第几章节
     */
    private Integer chapterNumber;
}
