package com.book.novel.module.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2021/2/27
 * @Description:
 */

@Data
@AllArgsConstructor
public class HistoryAuthorDTO {

    private String name;

    private Integer value;

    private List<HistoryBookDTO> children;

}
