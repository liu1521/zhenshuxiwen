package com.book.novel.module.eshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2021/2/24
 * @Description:
 */

@Data
public class GoodsQueryDTO {

    @ApiModelProperty("商品类别")
    private String type;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品作者")
    private String author;

    @ApiModelProperty("卖家ID")
    private Integer sellerId;

    private List<String> authors;

}
