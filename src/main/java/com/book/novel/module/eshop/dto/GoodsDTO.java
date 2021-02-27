package com.book.novel.module.eshop.dto;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description:
 */

@Data
public class GoodsDTO {

    private Integer cid;

    private String name;

    private String cover;

    private String author;

    private String introduce;

    private String tag;

    private Float price;

}
