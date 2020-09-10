package com.book.novel.module.chapter;

import com.book.novel.common.anno.NeedAdmin;
import com.book.novel.common.anno.NeedAuthor;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import com.book.novel.module.chapter.dto.ChapterExamineDTO;
import com.book.novel.module.chapter.dto.ChapterQueryDTO;
import com.book.novel.module.chapter.vo.ChapterUploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 章节相关路由
 */

@Api(tags = "小说章节相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header",dataType = "string",dataTypeClass = String.class)})
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "按小说id分页查询小说章节目录")
    @PostMapping("/api/chapter/listByNovelId")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<ChapterCatalogDTO>> listChapterByNovelId(@Valid @RequestBody ChapterQueryDTO pageParamDTO) {
        return chapterService.listChapterByNovelId(pageParamDTO);
    }

    @ApiOperation(value = "根据章节id获取章节详情")
    @PostMapping("/api/chapter/getChapterDetailByChapterId")
    @NoNeedLogin
    public ResponseDTO<ChapterDetailDTO> getChapterDetailByChapterId(@RequestParam Integer chapterId) {
        return chapterService.getChapterDetailByChapterId(chapterId);
    }

    @ApiOperation(value = "上传章节")
    @PostMapping("/api/chapter/upload")
    @NeedAuthor
    public ResponseDTO uploadChapter(@Valid @RequestBody ChapterUploadVO chapterUploadVO, HttpServletRequest request) {
        return chapterService.saveChapter(chapterUploadVO, request);
    }

    @ApiOperation(value = "获取所有未审核章节")
    @GetMapping("/api/chapter/admin/unExamine/get")
    @NeedAdmin
    public ResponseDTO<PageResultDTO<ChapterExamineDTO>> listChapterUnExamine(@Valid @RequestBody PageParamDTO pageParamDTO) {
        return chapterService.listChapterUnExamine(pageParamDTO);
    }

    @ApiOperation(value = "章节审核通过")
    @PostMapping("/api/chapter/admin/examine/success")
    @NeedAdmin
    public ResponseDTO createChapterSuccess(@RequestParam("chapterId") Integer chapterId) {
        return chapterService.updateChapterStatus(chapterId, true);
    }

    @ApiOperation(value = "章节审核拒绝")
    @PostMapping("/api/chapter/admin/examine/fail")
    @NeedAdmin
    public ResponseDTO createChapterFail(@RequestParam("chapterId") Integer chapterId) {
        return chapterService.updateChapterStatus(chapterId, false);
    }
}
