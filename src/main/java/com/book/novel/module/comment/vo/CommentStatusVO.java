package com.book.novel.module.comment.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ljs
 * @Date: 2020/8/13
 * @Description: 审核
 */
@Data
public class CommentStatusVO {
    /**
     * 主键id
     */
    @NotNull
    private Integer id;
    /**
     * 审核状态  0未审核 1审核通过 2审核未通过
     */
    @NotNull
    private  Integer status;
}
