package com.book.novel.module.chapter;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.chapter.dto.ChapterCatalogDTO;
import com.book.novel.module.chapter.dto.ChapterDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO<PageResultDTO<ChapterCatalogDTO>> listChapterByNovelId(@Valid @RequestBody PageParamDTO pageParamDTO, @RequestParam Integer novelId) {
        return chapterService.listChapterByNovelId(pageParamDTO, novelId);
    }

    @ApiOperation(value = "根据章节id获取章节详情")
    @PostMapping("/api/chapter/getChapterDetailByChapterId")
    @NoNeedLogin
    public ResponseDTO<ChapterDetailDTO> getChapterDetailByChapterId(@RequestParam Integer chapterId) {
        return chapterService.getChapterDetailByChapterId(chapterId);
    }

}
