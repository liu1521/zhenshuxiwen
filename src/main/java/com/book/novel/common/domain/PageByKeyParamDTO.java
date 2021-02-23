package com.book.novel.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description:
 */

@Data
public class PageByKeyParamDTO extends PageParamDTO {

    @NotNull
    @ApiModelProperty(value = "搜索的关键字", example = "圣墟")
    private String key;
}
