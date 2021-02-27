package com.book.novel.module.eshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 修改购物车商品数量DTO
 */

@Data
public class ShoppingCarGoodsNumDTO {

    @ApiModelProperty("商品ID")
    private Integer cid;

    @ApiModelProperty("修改后的数量")
    private Integer newNum;

}
