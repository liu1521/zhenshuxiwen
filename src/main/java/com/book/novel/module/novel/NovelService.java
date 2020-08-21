package com.book.novel.module.novel;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.service.ImgFileService;
import com.book.novel.module.chapter.ChapterMapper;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.novel.bo.PageBO;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import com.book.novel.module.novel.entity.NovelEntity;
import com.book.novel.module.novel.vo.NovelCreateVO;
import com.book.novel.module.novel.vo.NovelInfoVO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.util.BeanUtil;
import com.book.novel.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    private ImgFileService imgFileService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(PageParamDTO pageParamDTO, Integer categoryId) {
        // 查询总条数
        int totalCount = novelMapper.getNovelCountByCategory(categoryId);
        if (totalCount == 0) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_CATEGORY_ID_INVALID);
        }
        // 设置分页查询返回参数
        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        // 分页查询小说信息
        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novels = novelUserMapper.listNovelByCategory(pageBO, categoryId);

        this.setBase64Pic(novels);
        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByKey(PageParamDTO pageParamDTO, String key) {
        int totalCount = novelUserMapper.getNovelCountByKey(key);

        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novels = novelUserMapper.listNovelByKey(pageBO, key);

        this.setBase64Pic(novels);
        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    private PageResultDTO<NovelDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        return new PageResultDTO<>(currentPage, pageSize, pages);
    }

    public ResponseDTO<NovelDetailDTO> getNovelDetailById(Integer novelId) {
        NovelDetailDTO novelDetailDTO = novelUserMapper.getNovelDetailById(novelId);
        if (novelDetailDTO == null) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_ID_INVALID);
        }

        novelDetailDTO.setPic(imgFileService.getNovelCoverImg(novelDetailDTO.getPic()));
        return ResponseDTO.succData(novelDetailDTO);
    }

    public ResponseDTO<List<NovelDTO>> listRank(String rank_key, Integer num) {
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
            result = this.jsonList2NovelList(rankString);
        }

        return ResponseDTO.succData(result);
    }

    public void updateNovelHitsTo0(String column) {
        novelMapper.updateNovelHitsTo0(column);
    }

    public List<NovelDTO> updateNovelRank(String key, Integer num) {
        List<NovelDTO> rankNovelDTOList = novelUserMapper.listRank(key, num);
        if (CollectionUtils.isEmpty(rankNovelDTOList)) {
            return null;
        }
        this.setBase64Pic(rankNovelDTOList);
        List<String> rankNovelStringList = this.novelList2JsonList(rankNovelDTOList);
        // 刷新缓存
        redisValueOperations.getOperations().delete(key);
        redisValueOperations.getOperations().opsForList().rightPushAll(key, rankNovelStringList);
        return rankNovelDTOList;
    }

    private void setBase64Pic(List<NovelDTO> list) {
        list.forEach(novelDTO -> novelDTO.setPic(imgFileService.getNovelCoverImg(novelDTO.getPic())));
    }

    private List<String> novelList2JsonList(List<NovelDTO> novelDTOList) {
        List<String> result = new ArrayList<>();
        novelDTOList.forEach(novelDTO -> result.add(JsonUtil.toJson(novelDTO)));
        return result;
    }

    private List<NovelDTO> jsonList2NovelList(List<String> jsonList) {
        List<NovelDTO> result = new ArrayList<>();
        jsonList.forEach(json -> result.add((NovelDTO) JsonUtil.toObject(json, NovelDTO.class)));
        return result;
    }

    public ResponseDTO<PageResultDTO<NovelDTO>> listAllNovel(PageParamDTO pageParamDTO) {
        int totalCount = novelMapper.getNovelCount();

        PageResultDTO<NovelDTO> resultDTO = getPageResultDTO(pageParamDTO, totalCount);
        if (pageParamDTO.getSearchCount()) {
            resultDTO.setTotal(totalCount);
        }

        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novels = novelUserMapper.listAllNovel(pageBO);

        this.setBase64Pic(novels);
        resultDTO.setList(novels);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<List<NovelDetailDTO>> listNovelDetailDTOByAuthor(HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);
        List<NovelDetailDTO> novelDetailDTOList = novelUserMapper.listNovelDetailDTOByAuthorId(requestTokenBO.getRequestUserId());
        return ResponseDTO.succData(novelDetailDTOList);
    }

    public ResponseDTO saveNovel(NovelCreateVO novelCreateVO, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        NovelEntity novelEntity = BeanUtil.copy(novelCreateVO, NovelEntity.class);
        novelEntity.setAuthorId(10090);

        try{
            novelMapper.saveNovel(novelEntity);
        } catch (DataIntegrityViolationException e) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.ERROR_PARAM);
        }

        return ResponseDTO.succ();
    }

    public ResponseDTO uploadNovelCover(MultipartFile multipartFile) {
        String fileName = imgFileService.saveNovelCoverImg(multipartFile);
        if (StringUtils.isEmpty(fileName)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        return ResponseDTO.succData(fileName);
    }

    public ResponseDTO updateNovelInfo(NovelInfoVO novelInfoVO, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);
        if (! requestTokenBO.getRequestUserId().equals(novelInfoVO.getUserId())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        if (StringUtils.isEmpty(novelInfoVO.getPic())) {
            novelInfoVO.setPic("default.jpg");
        }
        Integer ret;
        try {
            ret = novelMapper.updateNovelById(novelInfoVO);
        } catch (DataIntegrityViolationException e) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }
        if (ret == 0) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }
        return ResponseDTO.succ();
    }

    public ResponseDTO deleteNovelByNovelId(Integer novelId, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        Integer authorId = novelMapper.getAuthorIdByNovelId(novelId);
        if (! requestTokenBO.getRequestUserId().equals(authorId)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        chapterMapper.deleteChapterByNovelId(novelId);
        novelMapper.deleteNovelByNovelId(novelId);
        return ResponseDTO.succ();
    }
}
