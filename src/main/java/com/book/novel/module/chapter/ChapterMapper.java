package com.book.novel.module.chapter;

import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.entity.ChapterEntity;
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
public interface ChapterMapper {

    Integer getChapterCountByNovelId(@Param("novelId") Integer novelId);

    List<ChapterCatalogDTO> listChapterByNovelId(@Param("pageBO") PageBO pageBO, @Param("novelId") Integer novelId);

    void deleteChapterByNovelId(@Param("novelId") Integer novelId);

    Integer getChapterNumByNovelId(@Param("novelId") Integer novelId);

    void saveChapter(ChapterEntity chapterEntity);
}
