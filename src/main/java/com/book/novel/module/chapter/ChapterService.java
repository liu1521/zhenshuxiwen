package com.book.novel.module.chapter;

import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.chapter.constant.ChapterResponseCodeConstant;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import com.book.novel.module.novel.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 章节相关业务
 */

@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private ChapterNovelMapper chapterNovelMapper;

    public ResponseDTO<PageResultDTO<ChapterCatalogDTO>> listChapterByNovelId(PageParamDTO pageParamDTO, Integer novelId) {
        int totalCount = chapterMapper.getChapterCountByNovelId(novelId);
        if (totalCount == 0) {
            return ResponseDTO.wrap(ChapterResponseCodeConstant.NOVEL_ID_VALID);
        }
        PageResultDTO<ChapterCatalogDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        int start = (resultDTO.getCurrentPage()-1) * resultDTO.getPageSize();
        List<ChapterCatalogDTO> chapters = chapterMapper.listChapterByNovelId(start, resultDTO.getPageSize(), novelId);

        resultDTO.setList(chapters);
        return ResponseDTO.succData(resultDTO);
    }

    private PageResultDTO<ChapterCatalogDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
        PageResultDTO<ChapterCatalogDTO> resultDTO = new PageResultDTO<>(currentPage, pageSize, pages);
        return resultDTO;
    }

    public ResponseDTO<ChapterDetailDTO> getByChapterId(Integer chapterId) {
        ChapterDetailDTO chapterDetailDTO = chapterNovelMapper.getByChapterId(chapterId);
        novelMapper.updateHits(chapterDetailDTO.getNovelId());

        return ResponseDTO.succData(chapterDetailDTO);
    }
}
