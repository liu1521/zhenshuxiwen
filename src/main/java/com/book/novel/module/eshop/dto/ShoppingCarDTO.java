package com.book.novel.module.eshop.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 购物车DTO
 */

@Data
public class ShoppingCarDTO {

    private Integer cid;

    private String name;

    private String cover;

    private String introduce;

    private Float price;

    private String tag;

    private Integer num;

    private String seller;

    private Integer sellerId;

    private LocalDateTime createTime;

}
