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

    Integer getNovelCountByCategory(@Param("categoryId") Integer categoryId);

    void updateHits(@Param("novelId") Integer novelId);

    void updateNovelHitsTo0(@Param("column") String column);

    Integer getNovelCount();

    void updateFavorites(@Param("favorites") Integer favorites, @Param("novelId") Integer novelId);

    void updateAddRecommend(@Param("novelId") Integer novelId);
}
