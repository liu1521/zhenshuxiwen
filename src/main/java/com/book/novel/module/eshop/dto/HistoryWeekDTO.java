package com.book.novel.module.eshop.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: ljs
 * @Date: 2021/2/27
 * @Description:
 */
@Data
public class HistoryWeekDTO {
    private LocalDate dayTime;
    private Integer number;
}
