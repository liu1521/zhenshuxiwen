package com.book.novel.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 分页查询参数DTO
 */

@Data
public class PageParamDTO {

    @NotNull(message = "当前页码不能为空")
    @ApiModelProperty(value = "页码(不能为空)", example = "1")
    private Integer currentPage;

    @NotNull(message = "每页数量不能为空")
    @ApiModelProperty(value = "每页数量(不能为空)", example = "10")
    @Max(value = 200, message = "每页最大为200")
    protected Integer pageSize;

    @ApiModelProperty("是否查询总条数")
    protected Boolean searchCount;

}
