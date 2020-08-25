package com.book.novel.module.novel.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/11
 * @Description: 小说详情DTO
 */

@Data
public class NovelDetailDTO {

    private Integer id;

    private String title;

    private String categoryName;

    private String author;

    private String pic;

    private String introduce;

    private String tag;

    private Integer up;

    private Integer down;

    private Integer hits;

    private Integer hitsDay;

    private Integer hitsWeek;

    private Integer hitsMonth;

    private Integer rating;

    private Integer serialize;

    private Integer favorites;

    private Date updateTime;

    private Integer word;

    private Integer recommend;

    private Integer status;

}
