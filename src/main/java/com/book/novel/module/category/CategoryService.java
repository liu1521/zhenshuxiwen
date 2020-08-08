package com.book.novel.module.category;

import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.category.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说类型相关业务
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取小说类型
     * @return
     */
    public ResponseDTO<List<CategoryVO>> listCategory() {
        return ResponseDTO.succData(categoryMapper.listCategory());
    }
}
