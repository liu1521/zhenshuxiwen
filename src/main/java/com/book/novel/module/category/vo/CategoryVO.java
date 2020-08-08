package com.book.novel.module.category.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说类型VO
 */

@Data
public class CategoryVO {

    @ApiModelProperty("小说类型id")
    private Integer id;

    @ApiModelProperty("小说类型")
    private String categoryName;

}
