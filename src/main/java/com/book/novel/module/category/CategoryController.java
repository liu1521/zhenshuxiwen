package com.book.novel.module.category;

import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.category.vo.CategoryVO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说类型相关路由
 */

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequiresRoles("admin")
    @GetMapping("/category/get")
    public ResponseDTO<List<CategoryVO>> getCategory() {
        return categoryService.listCategory();
    }

}
