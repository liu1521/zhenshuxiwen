package com.book.novel.module.chapter;

import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import com.book.novel.module.chapter.dto.ChapterExamineDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 章节小说
 */

@Mapper
@Component
public interface ChapterNovelMapper {
    ChapterDetailDTO getChapterDetailByChapterId(@Param("chapterId") Integer chapterId);

    List<ChapterExamineDTO> listUnExamineChapter(@Param("pageBO") PageBO pageBO);
}
