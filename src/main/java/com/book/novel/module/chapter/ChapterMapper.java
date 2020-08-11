package com.book.novel.module.chapter;

import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
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

    int getChapterCountByNovelId(@Param("novelId") Integer novelId);

    List<ChapterCatalogDTO> listChapterByNovelId(@Param("start") int start, @Param("pageSize") Integer pageSize, @Param("novelId") Integer novelId);
}
