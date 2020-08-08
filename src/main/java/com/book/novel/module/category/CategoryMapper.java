package com.book.novel.module.category;

import com.book.novel.module.category.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 */

@Mapper
@Component
public interface CategoryMapper {

    /**
     * 获取小说类型
     * @return
     */
    List<CategoryVO> listCategory();
}
