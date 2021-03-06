package com.book.novel.module.comment.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: ljs
 * @Date: 2020/8/12
 * @Description:
 */

@Data
public class CommentDetailDTO {
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
     *  发表用户id
     */
    private Integer userId;

    /**
     * 评论者id
     */
    private String username;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 顶
     */
    private Integer up;

    /**
     * 所属小说id
     */
    private Integer novelId;
}
