package com.book.novel.module.novel;

import com.book.novel.common.anno.NeedUser;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class NovelController {

    @Autowired
    private NovelService novelService;

    @ApiOperation(value = "按类型分页查询小说")
    @PostMapping("/api/novel/listByCategory")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(@Valid @RequestBody PageParamDTO pageParamDTO, @RequestParam Integer categoryId) {
        return novelService.listNovelByCategory(pageParamDTO, categoryId);
    }

    @ApiOperation(value = "分页搜索查询小说")
    @PostMapping("/api/novel/listByKey")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByKey(@Valid @RequestBody PageParamDTO pageParamDTO, @RequestParam String key) {
        return novelService.listNovelByKey(pageParamDTO, key);
    }

    @ApiOperation(value = "根据小说id获取小说详情")
    @PostMapping("/api/novel/getNovelDetail")
    @NoNeedLogin
    public ResponseDTO<NovelDetailDTO> getNovelDetailById(@RequestParam Integer novelId) {
        return novelService.getNovelDetailById(novelId);
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
}
