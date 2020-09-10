package com.book.novel.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 分页查询DTO
 */

@Data
public class PageResultDTO<T> {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量")
    private Integer pageSize;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Integer total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Integer pages;

    private List<T> list;


    private PageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        if (totalCount == null) totalCount = 0;

        this.currentPage = pageParamDTO.getCurrentPage();
        this.pageSize = pageParamDTO.getPageSize();
        this.pages = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
        if (pageParamDTO.getSearchCount()) this.total = totalCount;
    }


    public static <T> PageResultDTO<T> instance(PageParamDTO pageParamDTO, Integer totalCount, List<T> list) {
        PageResultDTO<T> resultDTO = new PageResultDTO<>(pageParamDTO, totalCount);
        resultDTO.list = list;
        return resultDTO;
    }
}
