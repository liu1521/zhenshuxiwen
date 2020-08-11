package com.book.novel.module.novel;

import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(PageParamDTO pageParamDTO, Integer categoryId) {
        int totalCount = novelMapper.getNovelCountByCategory(categoryId);
        if (totalCount == 0) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_CATEGORY_ID_INVALID);
        }

        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        int start = (resultDTO.getCurrentPage()-1) * resultDTO.getPageSize();
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

        int start = (resultDTO.getCurrentPage()-1) * resultDTO.getPageSize();
        List<NovelDTO> novels = novelUserMapper.listNovelByKey(start, resultDTO.getPageSize(), key);
            
        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    private PageResultDTO<NovelDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
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
}
