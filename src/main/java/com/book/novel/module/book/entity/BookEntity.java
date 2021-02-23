package com.book.novel.module.book.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description:
 */

@Data
public class BookEntity {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String author;

    private String cover;

    private String pic;

    private Integer seller;

    private String tag;

    private Integer up;

    private Integer down;

    private Integer hits;

    private Integer rating;

    private Integer rating_count;

    private Integer favorites;

    private Date createTime;

    private Integer status;

    private Integer hitsDay;

    private Integer hitsWeek;

    private Integer hitsMonth;

    private Integer stock;
}
