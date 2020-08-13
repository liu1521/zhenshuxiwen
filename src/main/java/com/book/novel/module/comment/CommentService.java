package com.book.novel.module.comment;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.comment.dto.CommentDetailDTO;
import com.book.novel.module.comment.dto.CommentUserIdDTO;
import com.book.novel.module.comment.vo.CommentCreateVO;
import com.book.novel.module.comment.vo.CommentStatusVO;
import com.book.novel.module.comment.vo.CommentUpVO;
import com.book.novel.module.novel.dto.NovelDTO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 评论相关业务
 */

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     *
     * @param pageParamDTO
     * @param totalCount
     * @return
     */
    private PageResultDTO<CommentDetailDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        PageResultDTO<CommentDetailDTO> resultDTO = new PageResultDTO<>(currentPage, pageSize, pages);
        return resultDTO;
    }
    private PageResultDTO<CommentUserIdDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount,boolean isCommentUserIdDTO) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        PageResultDTO<CommentUserIdDTO> resultDTO = new PageResultDTO<>(currentPage, pageSize, pages);
        return resultDTO;
    }


    /**
     * 根据小说查询对应评论(分页)
     * @param pageParamDTO
     * @param novelId
     * @return
     */
    public ResponseDTO<PageResultDTO<CommentDetailDTO>>  listCommentByNovelIdOrderByUp(PageParamDTO pageParamDTO, Integer novelId){
        PageResultDTO<CommentDetailDTO> pageResultDTO = getPageResultDTO(pageParamDTO,commentMapper.getCountByNovelId(novelId));
        pageResultDTO.setList(commentMapper.listCommentByNovelIdOrderByUp((pageResultDTO.getCurrentPage()-1)*pageResultDTO.getPageSize(),pageResultDTO.getPageSize(),novelId));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据用户id查询评论(分页)
     * @param pageParamDTO
     * @param userId
     * @return
     */
    public ResponseDTO<PageResultDTO<CommentUserIdDTO>>  listCommentByUserIdOrderByUp(PageParamDTO pageParamDTO,Integer userId){
        PageResultDTO<CommentUserIdDTO> pageResultDTO = getPageResultDTO(pageParamDTO,commentMapper.getCountByUserId(userId),false);
        pageResultDTO.setList(commentMapper.listCommentByUserIdOrderByUp((pageResultDTO.getCurrentPage()-1)*pageResultDTO.getPageSize(),pageResultDTO.getPageSize(),userId));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 审核
     * @param commentStatusVO
     * @return
     */
    public ResponseDTO status(CommentStatusVO commentStatusVO){
        Integer integer = commentMapper.updateStatusById(commentStatusVO.getId(), commentStatusVO.getStatus());
        if(integer == 1){
            return ResponseDTO.succ();
        }
        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR,"审核失败");
    }

    /**
     * 点赞
     * @param commentUpVO
     * @return
     */
    public ResponseDTO up(CommentUpVO commentUpVO){
        Integer integer = commentMapper.updateUpById(commentUpVO.getId());
        if(integer == 1){
            return ResponseDTO.succ();
        }
        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR,"点赞失败");

    }

    /**
     * 添加评论
     * @param commentCreateVO
     * @return
     */
    public ResponseDTO insert(CommentCreateVO commentCreateVO){
        Integer insert = commentMapper.insert(commentCreateVO.getContent(), new Date(), commentCreateVO.getUserId(), commentCreateVO.getNovelId());
        if(insert == 1){
            return ResponseDTO.succ();
        }
        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR,"评论失败");
    }
}
