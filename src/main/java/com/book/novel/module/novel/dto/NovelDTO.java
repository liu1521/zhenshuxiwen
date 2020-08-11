package com.book.novel.module.novel.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 小说信息DTO
 */

@Data
public class NovelDTO {

    private Integer id;

    private String title;

    private String author;

    private String pic;

    private String introduce;

    private String tag;

    private Double rating;

    private Integer serialize;

    private Integer favorites;

}
