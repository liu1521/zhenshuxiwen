package com.book.novel.module.novel;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: liu
 * @Date: 2020/8/7
 */

@Mapper
@Component
public interface NovelMapper {

    int getNovelCountByCategory(@Param("categoryId") Integer categoryId);
}
