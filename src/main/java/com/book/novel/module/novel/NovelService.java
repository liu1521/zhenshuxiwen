package com.book.novel.module.novel;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.common.service.ImgFileService;
import com.book.novel.module.chapter.ChapterMapper;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.common.constant.ExamineStatusEnum;
import com.book.novel.module.novel.dto.*;
import com.book.novel.module.novel.entity.NovelEntity;
import com.book.novel.module.novel.vo.NovelCreateVO;
import com.book.novel.module.novel.vo.NovelInfoVO;
import com.book.novel.util.BeanUtil;
import com.book.novel.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
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

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByCategory(CategoryNovelQueryDTO pageParamDTO) {
        // 查询总条数
        int totalCount = novelMapper.getNovelCountByCategory(pageParamDTO.getCategoryId());
        if (totalCount == 0) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.NOVEL_CATEGORY_ID_INVALID);
        }
        // 分页查询小说信息
        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novelDTOList = novelUserMapper.listNovelByCategory(pageBO, pageParamDTO.getCategoryId());
        this.setBase64Pic(novelDTOList);

        // 设置分页查询返回参数
        PageResultDTO<NovelDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount,novelDTOList);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<PageResultDTO<NovelDTO>> listNovelByKey(KeyNovelQueryDTO pageParamDTO) {
        int totalCount = novelUserMapper.getNovelCountByKey(pageParamDTO.getKey());

        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novelDTOList = novelUserMapper.listNovelByKey(pageBO, pageParamDTO.getKey());
        this.setBase64Pic(novelDTOList);
        // 设置分页查询返回参数
        PageResultDTO<NovelDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount, novelDTOList);
        return ResponseDTO.succData(resultDTO);
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

    private void setBase64Pic(List<NovelDTO> novelDTOList) {
        novelDTOList.forEach(novelDTO -> novelDTO.setPic(imgFileService.getNovelCoverImg(novelDTO.getPic())));
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


        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDTO> novelDTOList = novelUserMapper.listAllNovel(pageBO);
        this.setBase64Pic(novelDTOList);

        PageResultDTO<NovelDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount, novelDTOList);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO<List<NovelDetailDTO>> listNovelDetailDTOByAuthor(HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);
        List<NovelDetailDTO> novelDetailDTOList = novelUserMapper.listNovelDetailDTOByAuthorId(requestTokenBO.getRequestUserId());
        novelDetailDTOList.forEach(novelDetailDTO -> novelDetailDTO.setPic(imgFileService.getNovelCoverImg(novelDetailDTO.getPic())));
        return ResponseDTO.succData(novelDetailDTOList);
    }

    public ResponseDTO saveNovel(NovelCreateVO novelCreateVO, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        NovelEntity novelEntity = BeanUtil.copy(novelCreateVO, NovelEntity.class);
        novelEntity.setAuthorId(requestTokenBO.getRequestUserId());

        try{
            novelMapper.saveNovel(novelEntity);
        } catch (DataIntegrityViolationException e) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.ERROR_PARAM);
        }

        return ResponseDTO.succ();
    }

    public ResponseDTO uploadNovelCover(MultipartFile multipartFile) {
        if (multipartFile == null) return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
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

    public ResponseDTO<PageResultDTO<NovelExamineDTO>> listNovelUnExamine(PageParamDTO pageParamDTO) {
        Integer totalCount = novelMapper.countUnExamineNovel();


        PageBO pageBO = new PageBO(pageParamDTO);
        List<NovelDetailDTO> novelDetailDTOList = novelMapper.listNovelUnExamine(pageBO);

        List<NovelExamineDTO> novelExamineDTOList = new ArrayList<>();
        novelDetailDTOList.forEach(novelDetailDTO -> {
            novelDetailDTO.setPic(imgFileService.getNovelCoverImg(novelDetailDTO.getPic()));
            novelExamineDTOList.add(new NovelExamineDTO(novelDetailDTO));
        });

        PageResultDTO<NovelExamineDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount, novelExamineDTOList);
        return ResponseDTO.succData(resultDTO);
    }

    public ResponseDTO updateNovelStatus(Integer novelId, boolean success) {
        if (success) {
            novelMapper.updateNovelStatus(ExamineStatusEnum.EXAMINE_SUCCESS.getValue(), novelId);

            return ResponseDTO.wrap(NovelResponseCodeConstant.EXAMINE_SUCCESS);
        } else {
            novelMapper.updateNovelStatus(ExamineStatusEnum.EXAMINE_FAIL.getValue(), novelId);

            return ResponseDTO.wrap(NovelResponseCodeConstant.EXAMINE_FAIL);
        }
    }
}
