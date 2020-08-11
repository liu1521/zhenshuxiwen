package com.book.novel.module.novel;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
