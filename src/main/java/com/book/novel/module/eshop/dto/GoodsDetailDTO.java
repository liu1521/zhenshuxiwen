package com.book.novel.module.eshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 商品详情DTO
 */

@Data
public class GoodsDetailDTO {

    @ApiModelProperty("商品id")
    private Integer cid;

    @ApiModelProperty("小说名称")
    private String name;

    @ApiModelProperty("小说封面")
    private String cover;

    @ApiModelProperty("小说作者")
    private String author;

    @ApiModelProperty("小说描述")
    private String introduce;

    @ApiModelProperty("小说标签")
    private String tag;

    @ApiModelProperty("小说价格")
    private Float price;

    private String seller;

    private String sellerId;

    private Integer monthlySales;

    private Integer totalSales;
}
