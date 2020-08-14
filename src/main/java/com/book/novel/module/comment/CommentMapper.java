package com.book.novel.module.comment;

import com.book.novel.module.comment.dto.CommentDetailDTO;
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

    List<CommentDetailDTO> listCommentByNovelIdOrderByUp(@Param("start") Integer start, @Param("pageSize") Integer pageSize,@Param("novelId")Integer novelId);
    Integer getCountByNovelId(@Param("novelId") Integer novelId);

}
