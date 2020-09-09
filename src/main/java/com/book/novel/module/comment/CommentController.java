package com.book.novel.module.comment;

import com.book.novel.common.anno.NeedAdmin;
import com.book.novel.common.anno.NeedUser;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.comment.dto.CommentDetailDTO;
import com.book.novel.module.comment.dto.CommentQueryByNovelIdDTO;
import com.book.novel.module.comment.dto.CommentQueryByUserIdDTO;
import com.book.novel.module.comment.dto.CommentUserIdDTO;
import com.book.novel.module.comment.vo.CommentCreateVO;
import com.book.novel.module.comment.vo.CommentStatusVO;
import com.book.novel.module.comment.vo.CommentUpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 评论相关路由
 */

@Api(tags = "评论相关操作接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header",dataType = "string",dataTypeClass = String.class)})
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("按小说id查询评论(点赞排序)分页")
    @PostMapping("/api/comment/listCommentByNovelIdOrderByUp")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<CommentDetailDTO>>  listCommentByNovelIdOrderByUp(@Valid @RequestBody CommentQueryByNovelIdDTO pageParamDTO){
        return commentService.listCommentByNovelIdOrderByUp(pageParamDTO);
    }

    @ApiOperation("按用户id查询评论(点赞排序)分页")
    @PostMapping("/api/comment/listCommentByUserIdOrderByUp")
    @NeedUser
    public ResponseDTO<PageResultDTO<CommentUserIdDTO>>  listCommentByUserIdOrderByUp(@Valid @RequestBody CommentQueryByUserIdDTO pageParamDTO){
        return commentService.listCommentByUserIdOrderByUp(pageParamDTO);
    }

    @ApiOperation("点赞")
    @PostMapping("/api/comment/up")
    @NeedUser
    public ResponseDTO up(@Valid @RequestBody CommentUpVO commentUpVO){
        return commentService.up(commentUpVO);
    }

    @ApiOperation("审核")
    @PostMapping("/api/comment/status")
    @NeedAdmin
    public ResponseDTO up(@Valid @RequestBody CommentStatusVO commentStatusVO){
        return commentService.status(commentStatusVO);
    }

    @ApiOperation("添加评论")
    @PostMapping("/api/comment/create")
    @NeedUser
    public ResponseDTO create(@Valid @RequestBody CommentCreateVO  commentCreateVO){
        return commentService.insert(commentCreateVO);
    }



}
