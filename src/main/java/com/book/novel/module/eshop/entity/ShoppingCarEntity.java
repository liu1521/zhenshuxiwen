package com.book.novel.module.eshop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 购物车实体类
 */

@Data
@Builder
public class ShoppingCarEntity {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否已结算(0：未结算；1：已结算)
     */
    private Integer settled;

}
