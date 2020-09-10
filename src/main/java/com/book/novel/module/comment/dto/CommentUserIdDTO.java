package com.book.novel.module.comment.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: ljs
 * @Date: 2020/8/13
 * @Description:
 */

@Data
public class CommentUserIdDTO {
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
     * 用户id
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
     * 所属小说
     */
    private Integer novelId;
}
