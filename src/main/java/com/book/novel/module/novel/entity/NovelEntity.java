package com.book.novel.module.novel.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说实体类
 */
public class NovelEntity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 类型
     */
    private Integer categoryId;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 封面url  /开头表示图片在磁盘中
     */
    private String pic;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 标签  不同标签用逗号分割
     */
    private String tag;

    /**
     * 顶
     */
    private Integer up;

    /**
     * 踩
     */
    private Integer down;


    /**
     * 总浏览量
     */
    private Integer hits;

    /**
     * 评分  rating=(rating*ratingCount+curRating)/(ratingCount+1)
     */
    private Double rating;

    /**
     * 评分人数
     */
    private Integer ratingCount;

    /**
     * 连载状态  0连载中 1已完结
     */
    private Integer serialize;

    /**
     * 收藏人数
     */
    private Integer favorites;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近章节更新时间
     */
    private Date updateTime;

    /**
     * 审核状态  0未审核 1审核通过 2审核未通过
     */
    private Integer status;

    /**
     * 日浏览量  0点更新
     */
    private Integer hitsDay;

    /**
     * 周浏览量  周一0点更新
     */
    private Integer hitsWeek;

    /**
     * 月浏览量  1号0点更新
     */
    private Integer hitsMonth;

    /**
     * 总字数
     */
    private Integer word;

    /**
     * 推荐票
     */
    private Integer recommend;

}
