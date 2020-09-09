package com.book.novel.module.comment;

import com.book.novel.module.comment.dto.CommentDetailDTO;
import com.book.novel.module.comment.dto.CommentUserIdDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 */

@Mapper
@Component
public interface CommentMapper {
    List<CommentDetailDTO> listCommentByNovelIdOrderByUp(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("novelId") Integer novelId);

    List<CommentUserIdDTO> listCommentByUserIdOrderByUp(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("userId") Integer userId);

    Integer getCountByNovelId(@Param("novelId") Integer novelId);

    Integer updateStatusById(@Param("id") Integer commentId, @Param("status") Integer status);

    Integer updateUpById(@Param("id") Integer commentId);

    Integer insert(@Param("content") String content, @Param("userId") Integer userId, @Param("novelId") Integer novelId);

    Integer getCountByUserId(@Param("userId") Integer userId);
}
