package com.book.novel.module.comment;

import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.comment.dto.CommentDetailDTO;
import com.book.novel.module.novel.dto.NovelDTO;
import io.swagger.models.auth.In;
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
    private CommentMapper commentMapper;
    private PageResultDTO<CommentDetailDTO> getPageResultDTO(PageParamDTO pageParamDTO, Integer totalCount) {
        int pageSize = pageParamDTO.getPageSize();
        int currentPage = pageParamDTO.getCurrentPage();
        int pages = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        PageResultDTO<CommentDetailDTO> resultDTO = new PageResultDTO<>(currentPage, pageSize, pages);
        return resultDTO;
    }
    public ResponseDTO<PageResultDTO<CommentDetailDTO>>  listCommentByNovelIdOrderByUp(PageParamDTO pageParamDTO, Integer novelId){
        PageResultDTO<CommentDetailDTO> pageResultDTO = getPageResultDTO(pageParamDTO,commentMapper.getCountByNovelId(novelId));
        pageResultDTO.setList(commentMapper.listCommentByNovelIdOrderByUp((pageResultDTO.getCurrentPage()-1)*pageResultDTO.getPageSize(),pageResultDTO.getPageSize(),novelId));
        return ResponseDTO.succData(pageResultDTO);
    }
}
