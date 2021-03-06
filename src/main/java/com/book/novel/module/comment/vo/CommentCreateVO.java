package com.book.novel.module.comment.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ljs
 * @Date: 2020/8/13
 * @Description:
 */
@Data
public class CommentCreateVO {

    /**
     * 正文
     */
    @NotNull(message = "内容不能为空")

    private String content;

    /**
     * 用户id
     */
    @NotNull
    private Integer userId;

    /**
     * 小说id
     */
    @NotNull
    private Integer novelId;
}
