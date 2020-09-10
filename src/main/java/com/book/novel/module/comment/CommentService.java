package com.book.novel.module.comment;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.service.ImgFileService;
import com.book.novel.module.comment.dto.CommentDetailDTO;
import com.book.novel.module.comment.dto.CommentQueryByNovelIdDTO;
import com.book.novel.module.comment.dto.CommentQueryByUserIdDTO;
import com.book.novel.module.comment.dto.CommentUserIdDTO;
import com.book.novel.module.comment.vo.CommentCreateVO;
import com.book.novel.module.comment.vo.CommentStatusVO;
import com.book.novel.module.comment.vo.CommentUpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 评论相关业务
 */

@Service
public class CommentService {

    @Autowired
    private ImgFileService imgFileService;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据小说查询对应评论(分页)
     * @param pageParamDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<CommentDetailDTO>>  listCommentByNovelIdOrderByUp(CommentQueryByNovelIdDTO pageParamDTO){
        List<CommentDetailDTO> commentDetailDTOList = commentMapper.listCommentByNovelIdOrderByUp((pageParamDTO.getCurrentPage() - 1) * pageParamDTO.getPageSize()
                , pageParamDTO.getPageSize()
                , pageParamDTO.getNovelId());
        commentDetailDTOList.forEach(commentDetailDTO -> commentDetailDTO.setHeadImg(imgFileService.getUserHeadImg(commentDetailDTO.getHeadImg())));

        PageResultDTO<CommentDetailDTO> pageResultDTO = PageResultDTO.instance(
                pageParamDTO,
                commentMapper.getCountByNovelId(pageParamDTO.getNovelId()),
                commentDetailDTOList
                );
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据用户id查询评论(分页)
     * @param pageParamDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<CommentUserIdDTO>>  listCommentByUserIdOrderByUp(CommentQueryByUserIdDTO pageParamDTO){
        PageResultDTO<CommentUserIdDTO> pageResultDTO = PageResultDTO.instance(
                pageParamDTO,
                commentMapper.getCountByUserId(pageParamDTO.getUserId()),
                commentMapper.listCommentByUserIdOrderByUp(
                        (pageParamDTO.getCurrentPage()-1)*pageParamDTO.getPageSize(),
                        pageParamDTO.getPageSize(),
                        pageParamDTO.getUserId())
                    );
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
        Integer insert = commentMapper.insert(commentCreateVO.getContent(), commentCreateVO.getUserId(), commentCreateVO.getNovelId());
        if(insert == 1){
            return ResponseDTO.succ();
        }
        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR,"评论失败");
    }
}
