package com.book.novel.module.chapter;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.chapter.constant.ChapterResponseCodeConstant;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import com.book.novel.module.chapter.dto.ChapterExamineDTO;
import com.book.novel.module.chapter.dto.ChapterQueryDTO;
import com.book.novel.module.chapter.entity.ChapterEntity;
import com.book.novel.module.chapter.vo.ChapterUploadVO;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.novel.NovelMapper;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.common.constant.ExamineStatusEnum;
import org.apache.commons.lang3.StringUtils;
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
        Integer totalCount = chapterMapper.getChapterCountByNovelId(pageParamDTO.getNovelId());
        if (totalCount == 0) {
            return ResponseDTO.wrap(ChapterResponseCodeConstant.NOVEL_ID_VALID);
        }

        if (pageParamDTO.getPageSize()  < 0) pageParamDTO.setPageSize(totalCount);
        PageBO pageBO = new PageBO(pageParamDTO);
        List<ChapterCatalogDTO> catalogDTOList = chapterMapper.listChapterByNovelId(pageBO, pageParamDTO.getNovelId());

        PageResultDTO<ChapterCatalogDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount, catalogDTOList);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<ChapterDetailDTO> getChapterDetailByChapterId(Integer chapterId, HttpServletRequest request) {
        ChapterDetailDTO chapterDetailDTO = chapterNovelMapper.getChapterDetailByChapterId(chapterId);

        novelMapper.updateHits(chapterDetailDTO.getNovelId());
        String token = loginTokenService.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            RequestTokenBO userBO = loginTokenService.getUserTokenInfo(token);
            novelMapper.saveHistory(userBO.getRequestUserId(), chapterDetailDTO.getNovelId());
        }
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

        novelMapper.updateNovelWordAddByNovelId(chapterUploadVO.getContent().length(), chapterUploadVO.getNovelId());

        return ResponseDTO.succ();
    }

    public ResponseDTO<PageResultDTO<ChapterExamineDTO>> listChapterUnExamine(PageParamDTO pageParamDTO) {
        Integer total = chapterMapper.countUnExamineChapter();

        PageBO pageBO = new PageBO(pageParamDTO);
        List<ChapterExamineDTO> chapterExamineDTOList = chapterNovelMapper.listUnExamineChapter(pageBO);

        PageResultDTO<ChapterExamineDTO> resultDTO = PageResultDTO.instance(pageParamDTO, total, chapterExamineDTOList);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO updateChapterStatus(Integer chapterId, boolean success) {
        if (success) {
            chapterMapper.updateChapterStatus(ExamineStatusEnum.EXAMINE_SUCCESS.getValue(), chapterId);

            return ResponseDTO.wrap(NovelResponseCodeConstant.EXAMINE_SUCCESS);
        } else {
            chapterMapper.updateChapterStatus(ExamineStatusEnum.EXAMINE_FAIL.getValue(), chapterId);

            return ResponseDTO.wrap(NovelResponseCodeConstant.EXAMINE_FAIL);
        }
    }
}
