package com.book.novel.module.chapter;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.chapter.constant.ChapterResponseCodeConstant;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import com.book.novel.module.chapter.dto.ChapterQueryDTO;
import com.book.novel.module.chapter.entity.ChapterEntity;
import com.book.novel.module.chapter.vo.ChapterUploadVO;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.novel.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 章节相关业务
 */

@Service
public class ChapterService {

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private ChapterNovelMapper chapterNovelMapper;

    public ResponseDTO<PageResultDTO<ChapterCatalogDTO>> listChapterByNovelId(ChapterQueryDTO pageParamDTO) {
        int totalCount = chapterMapper.getChapterCountByNovelId(pageParamDTO.getNovelId());
        if (totalCount == 0) {
            return ResponseDTO.wrap(ChapterResponseCodeConstant.NOVEL_ID_VALID);
        }
        PageResultDTO<ChapterCatalogDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        if (resultDTO.getPageSize()  < 0) resultDTO.setPageSize(totalCount);
        int start = (resultDTO.getCurrentPage()-1) * resultDTO.getPageSize();
        List<ChapterCatalogDTO> chapters = chapterMapper.listChapterByNovelId(start, resultDTO.getPageSize(), pageParamDTO.getNovelId());

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

    public ResponseDTO<ChapterDetailDTO> getChapterDetailByChapterId(Integer chapterId) {
        ChapterDetailDTO chapterDetailDTO = chapterNovelMapper.getChapterDetailByChapterId(chapterId);
        novelMapper.updateHits(chapterDetailDTO.getNovelId());

        return ResponseDTO.succData(chapterDetailDTO);
    }

    public ResponseDTO saveChapter(ChapterUploadVO chapterUploadVO, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        Integer authorId = novelMapper.getAuthorIdByNovelId(chapterUploadVO.getNovelId());
        if (! requestTokenBO.getRequestUserId().equals(authorId)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        Integer num = chapterMapper.getChapterNumByNovelId(chapterUploadVO.getNovelId());
        if (num == null) num = 0;

        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setNovelId(chapterUploadVO.getNovelId());
        chapterEntity.setContent(chapterUploadVO.getContent());
        chapterEntity.setTitle(chapterUploadVO.getTitle());
        chapterEntity.setChapterNumber(num+1);

        chapterMapper.saveChapter(chapterEntity);

        return ResponseDTO.succ();
    }
}
