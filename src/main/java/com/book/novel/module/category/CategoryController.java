package com.book.novel.module.category;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.category.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说类型相关路由
 */

@Api(tags = "小说类型相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, dataType = "string", paramType = "header")})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取小说分类类型")
    @GetMapping("/api/category/get")
    @NoNeedLogin
    public ResponseDTO<List<CategoryVO>> getCategory() {
        return categoryService.listCategory();
    }

}
