package com.book.novel.common.domain.bo;

import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.module.novel.constant.NovelOrderKeyConstant;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/14
 * @Description: 分页查询参数
 */

@Data
public class PageBO {

    private Integer start;

    private Integer pageSize;

    private String orderKey;

    public PageBO(PageParamDTO pageParamDTO) {
        this.pageSize = pageParamDTO.getPageSize();
        int page = pageParamDTO.getCurrentPage();
        start = (page-1) * pageSize;

        String paramOrderKey = pageParamDTO.getOrderKey();
        if (NovelOrderKeyConstant.orderKeyList().contains(paramOrderKey)) {
            orderKey = paramOrderKey;
        } else {
            orderKey = NovelOrderKeyConstant.UPDATE_TIME;
        }
    }
}
