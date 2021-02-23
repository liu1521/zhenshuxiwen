package com.book.novel.module.book.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description: 实体书DTO
 */

@Data
public class BookDTO {

    private Integer id;

    private String category;

    private String title;

    private String author;

    private String cover;

    private String pic;

    private String seller;

    private String tag;

    private Integer up;

    private Integer down;

    private Integer rating;

    private Integer favorites;

    private Date createTime;

    private Integer hitsDay;

    private Integer hitsWeek;

    private Integer hitsMonth;

    private Integer stock;

}
