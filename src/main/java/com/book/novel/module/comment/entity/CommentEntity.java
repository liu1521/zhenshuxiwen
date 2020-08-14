package com.book.novel.module.comment.entity;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description:
 */
public class CommentEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论者id
     */
    private Integer userId;

    /**
     * 审核状态  0未审核 1审核通过 2审核未通过
     */
    private Integer status;

    /**
     * 顶
     */
    private Integer up;

    /**
     * 所属小说id
     */
    private Integer novelId;
}
