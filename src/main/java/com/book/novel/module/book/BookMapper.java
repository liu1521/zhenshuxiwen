package com.book.novel.module.book;

import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.book.dto.BookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description:
 */

@Mapper
@Component
public interface BookMapper {

    List<BookDTO> listBookByPage(@Param("pageBO") PageBO pageBO);

    Integer getGBookCount(String key);

    BookDTO getBookById(@Param("bookId") Integer bookId);

    List<BookDTO> listBookByKey(@Param("pageBO") PageBO pageBO, @Param("key") String key);
}
