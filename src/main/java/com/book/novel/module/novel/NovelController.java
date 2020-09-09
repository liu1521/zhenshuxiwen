package com.book.novel.module.novel;

import com.book.novel.common.anno.NeedAuthor;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.dto.CategoryNovelQueryDTO;
import com.book.novel.module.novel.dto.KeyNovelQueryDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import com.book.novel.module.novel.vo.NovelCreateVO;
import com.book.novel.module.novel.vo.NovelInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说相关路由
 */

@Api(tags = "小说操作相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header",dataType = "string",dataTypeClass = String.class)})
public class NovelController {

    @Autowired
    private NovelService novelService;

    @ApiOperation(value = "获取全部小说", notes = "默认按更新时间排序")
    @PostMapping("/api/novel/all/get")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<NovelDTO>> listAllNovel(@Valid @RequestBody PageParamDTO pageParamDTO) {
        return novelService.listAllNovel(pageParamDTO);
    }

    @ApiOperation(value = "按类型分页查询小说")
    @PostMapping("/api/novel/listByCategory")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(@Valid @RequestBody CategoryNovelQueryDTO pageParamDTO) {
        return novelService.listNovelByCategory(pageParamDTO);
    }

    @ApiOperation(value = "分页搜索查询小说")
    @PostMapping("/api/novel/listByKey")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByKey(@Valid @RequestBody KeyNovelQueryDTO pageParamDTO) {
        return novelService.listNovelByKey(pageParamDTO);
    }

    @ApiOperation(value = "根据小说id获取小说详情")
    @PostMapping("/api/novel/getNovelDetail")
    @NoNeedLogin
    public ResponseDTO<NovelDetailDTO> getNovelDetailById(@RequestParam Integer novelId) {
        return novelService.getNovelDetailById(novelId);
    }

    @ApiOperation(value = "创建小说")
    @PostMapping("/api/novel/create")
    @NeedAuthor
    public ResponseDTO createNovel(@Valid @RequestBody NovelCreateVO novelCreateVO, HttpServletRequest request) {
        return novelService.saveNovel(novelCreateVO, request);
    }

    @ApiOperation(value = "上传小说封面")
    @PostMapping("/api/novel/cover/upload")
    @NeedAuthor
    public ResponseDTO uploadNovelCover(MultipartFile multipartFile) {
        return novelService.uploadNovelCover(multipartFile);
    }

    @ApiOperation(value = "修改小说信息")
    @PostMapping("/api/novel/update")
    @NeedAuthor
    public ResponseDTO updateNovelInfo(@Valid @RequestBody NovelInfoVO novelInfoVO, HttpServletRequest request) {
        return novelService.updateNovelInfo(novelInfoVO, request);
    }

    @ApiOperation(value = "删除小说")
    @PostMapping("/api/novel/delete")
    @NeedAuthor
    public ResponseDTO deleteNovelByNovelId(@RequestParam Integer novelId, HttpServletRequest request) {
        return novelService.deleteNovelByNovelId(novelId, request);
    }

    @ApiOperation(value = "获取总点击量前30小说")
    @GetMapping("/api/novel/rank/hits/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankHits() {
        return novelService.listRank(RedisKeyConstant.RANK_HITS, 30);
    }

    @ApiOperation(value = "获取日点击量前10小说")
    @GetMapping("/api/novel/rank/day/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankDay() {
        return novelService.listRank(RedisKeyConstant.RANK_DAY, 10);
    }

    @ApiOperation(value = "获取周点击量前10小说")
    @GetMapping("/api/novel/rank/week/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankWeek() {
        return novelService.listRank(RedisKeyConstant.RANK_WEEK, 10);
    }

    @ApiOperation(value = "获取月点击量前10小说")
    @GetMapping("/api/novel/rank/month/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankMonth() {
        return novelService.listRank(RedisKeyConstant.RANK_MONTH, 10);
    }

    @ApiOperation(value = "获取评分前10小说")
    @GetMapping("/api/novel/rank/rating/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankRating() {
        return novelService.listRank(RedisKeyConstant.RANK_RATING, 10);
    }

    @ApiOperation(value = "获取最多收藏的10本小说")
    @GetMapping("/api/novel/rank/favorites/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankFavorites() {
        return novelService.listRank(RedisKeyConstant.RANK_FAVORITES, 10);
    }

    @ApiOperation(value = "获取最受推荐的10本小说")
    @GetMapping("/api/novel/rank/recommend/get")
    @NoNeedLogin
    public ResponseDTO<List<NovelDTO>> listRankRecommend() {
        return novelService.listRank(RedisKeyConstant.RANK_RECOMMEND, 10);
    }

    @ApiOperation(value = "获取自己写的所有小说")
    @GetMapping("/api/novel/author/get")
    @NeedAuthor
    public ResponseDTO<List<NovelDetailDTO>> listNovelByAuthor(HttpServletRequest request) {
        return novelService.listNovelDetailDTOByAuthor(request);
    }
}
