package com.book.novel.module.category;

import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.category.vo.CategoryVO;
import com.book.novel.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    /**
     * 获取小说类型
     * @return
     */
    public ResponseDTO<List<CategoryVO>> listCategory() {
        ListOperations<String, String> listOperations = redisValueOperations.getOperations().opsForList();
        List<String> category = listOperations.range(RedisKeyConstant.CATEGORY, 0, -1);
        List<CategoryVO> categoryVOS;
        if (CollectionUtils.isEmpty(category)) {
            categoryVOS = categoryMapper.listCategory();
            categoryVOS.forEach(cvo -> listOperations.rightPush(RedisKeyConstant.CATEGORY, JsonUtil.toJson(cvo)));
        } else {
            categoryVOS = new ArrayList<>();
            category.forEach(json -> categoryVOS.add((CategoryVO) JsonUtil.toObject(json, CategoryVO.class)));
        }
        return ResponseDTO.succData(categoryVOS);
    }
}
