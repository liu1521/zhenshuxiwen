package com.book.novel.module.comment;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.comment.dto.CommentDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 评论相关路由
 */

@Api(tags = "评论相关操作接口")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("按小说id查询评论(点赞排序)分页")
    @PostMapping("/api/comment/listCommentByNovelIdOrderByUp")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<CommentDetailDTO>>  listCommentByNovelIdOrderByUp(@Valid @RequestBody PageParamDTO pageParamDTO, @RequestParam Integer novelId){
        return commentService.listCommentByNovelIdOrderByUp(pageParamDTO,novelId);
    }
}
