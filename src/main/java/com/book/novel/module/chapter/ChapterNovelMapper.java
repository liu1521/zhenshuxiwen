package com.book.novel.module.chapter;

import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 章节小说
 */

@Mapper
@Component
public interface ChapterNovelMapper {
    ChapterDetailDTO getByChapterId(@Param("chapterId") Integer chapterId);
}
