package com.book.novel.module.eshop.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2021/2/27
 * @Description:
 */

@Data
public class HistoryAuthorDTO {

    private String name;

    private String value;

    private List<HistoryBookDTO> children;

}
