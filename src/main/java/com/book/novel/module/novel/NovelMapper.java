package com.book.novel.module.novel;

import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import com.book.novel.module.novel.entity.NovelEntity;
import com.book.novel.module.novel.vo.NovelInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

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

    void saveNovel(NovelEntity novelEntity);

    Integer updateNovelById(NovelInfoVO novelInfoVO);

    void deleteNovelByNovelId(@Param("novelId") Integer novelId);

    Integer getAuthorIdByNovelId(@Param("novelId") Integer novelId);

    NovelEntity getByNovelId(@Param("novelId") Integer novelId);

    Integer countUnExamineNovel();

    List<NovelDetailDTO> listNovelUnExamine(@Param("pageBO") PageBO pageBO);

    void updateNovelStatus(@Param("status") Integer status, @Param("novelId") Integer novelId);

    void updateNovelWordAddByNovelId(@Param("wordNum") Integer length, @Param("novelId") Integer novelId);

    void saveHistory(Integer uid, Integer nid);

    List<Integer> listNovelIdByUserId(Integer uid);

    List<String> listAuthorNameByNovelId(@Param("favoriteNovel") List<Integer> favoriteNovel);
}
