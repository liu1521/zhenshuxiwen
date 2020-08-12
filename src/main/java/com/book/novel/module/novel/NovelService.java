package com.book.novel.module.novel;

import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import com.book.novel.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 小说相关业务
 */

@Service
public class NovelService {

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private NovelUserMapper novelUserMapper;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(PageParamDTO pageParamDTO, Integer categoryId) {
        int totalCount = novelMapper.getNovelCountByCategory(categoryId);
        if (totalCount == 0) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_CATEGORY_ID_INVALID);
        }

        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        int start = (resultDTO.getCurrentPage() - 1) * resultDTO.getPageSize();
        List<NovelDTO> novels = novelUserMapper.listNovelByCategory(start, resultDTO.getPageSize(), categoryId);

        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByKey(PageParamDTO pageParamDTO, String key) {
        int totalCount = novelUserMapper.getNovelCountByKey(key);
        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        int start = (resultDTO.getCurrentPage() - 1) * resultDTO.getPageSize();
        List<NovelDTO> novels = novelUserMapper.listNovelByKey(start, resultDTO.getPageSize(), key);

        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    private PageResultDTO<NovelDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        PageResultDTO<NovelDTO> resultDTO = new PageResultDTO<>(currentPage, pageSize, pages);
        return resultDTO;
    }

    public ResponseDTO<NovelDetailDTO> getNovelDetailById(Integer novelId) {
        NovelDetailDTO novelDetailDTO = novelUserMapper.getNovelDetailById(novelId);
        if (novelDetailDTO == null) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_ID_INVALID);
        }

        return ResponseDTO.succData(novelDetailDTO);
    }

    public ResponseDTO<List<NovelDTO>> getRank(String rank_key, Integer num) {
        List<String> rankString = redisValueOperations.getOperations().opsForList().range(rank_key, 0, -1);
        List<NovelDTO> result;
        if (CollectionUtils.isEmpty(rankString)) {
            // 缓存中没有rank信息,从db中拉取并缓存
            result = updateNovelRank(rank_key, num);
            if (result == null) {
                return ResponseDTO.wrap(NovelResponseCodeConstant.RANK_ERROR);
            }
        } else {
            // 缓存中有rank信息,将字符串对象转为NovelDTO返回
            result = new ArrayList<>();
            rankString.forEach(novelStr -> result.add((NovelDTO) JsonUtil.toObject(novelStr, NovelDTO.class)));
        }

        return ResponseDTO.succData(result);
    }

    public void updateNovelHitsTo0(String column) {
        novelMapper.updateNovelHitsTo0(column);
    }

    public List<NovelDTO> updateNovelRank(String key, Integer num) {
        List<NovelDTO> rankNovelDTOList = novelUserMapper.getRank(key, num);
        if (CollectionUtils.isEmpty(rankNovelDTOList)) {
            return null;
        }
        List<String> rankNovelStringList = new ArrayList<>();
        rankNovelDTOList.forEach(novelDTO -> rankNovelStringList.add(JsonUtil.toJson(novelDTO)));
        redisValueOperations.getOperations().delete(key);
        redisValueOperations.getOperations().opsForList().rightPushAll(key, rankNovelStringList);
        return rankNovelDTOList;
    }
}
