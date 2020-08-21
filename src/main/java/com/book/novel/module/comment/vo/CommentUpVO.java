package com.book.novel.module.comment.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ljs
 * @Date: 2020/8/13
 * @Description: 点赞
 */
@Data
public class CommentUpVO {
    /**
     * 主键id
     */
    @NotNull
    private Integer id;


}
