package com.book.novel.module.novel.dto;

import com.book.novel.common.domain.PageParamDTO;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/9/9
 * @Description:
 */

@Data
public class CategoryNovelQueryDTO extends PageParamDTO {

    private Integer categoryId;

}
