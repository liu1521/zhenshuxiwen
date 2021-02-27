package com.book.novel.module.eshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: ljs
 * @Date: 2021/2/27
 * @Description:
 */
@Data
@AllArgsConstructor
public class HistoryAuthorBO {
    private String title;
    private String username;
    private Integer novelId;
    private Integer value;
}
