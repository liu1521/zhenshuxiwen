package com.book.novel.module.category.entity;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description:
 */

@Data
public class CategoryEntity {

    /**
     * 主键id
     */
    private Integer id;


    /**
     * 类型名
     */
    private String categoryName;
}
