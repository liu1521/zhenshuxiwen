package com.book.novel.module.book;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.PageByKeyParamDTO;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.domain.bo.PageBO;
import com.book.novel.module.book.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description:
 */

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public ResponseDTO<PageResultDTO<BookDTO>> listBookByPage(PageParamDTO pageParamDTO) {
        int totalCount = bookMapper.getGBookCount(null);
        if (totalCount == 0) {
            return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_BUSY);
        }

        PageBO pageBO = new PageBO(pageParamDTO);
        List<BookDTO> bookDTOList = bookMapper.listBookByPage(pageBO);

        PageResultDTO<BookDTO> resultDTO = PageResultDTO.instance(pageParamDTO, totalCount, bookDTOList);
        return ResponseDTO.succData(resultDTO);
    }


    public ResponseDTO<BookDTO> getBookById(Integer bookId) {
        BookDTO bookById = bookMapper.getBookById(bookId);
        if(bookById==null)
            return ResponseDTO.wrap(ResponseCodeConst.NOT_EXISTS);
        return ResponseDTO.succData(bookById);
    }

    public ResponseDTO<PageResultDTO<BookDTO>> listBookByKey(PageByKeyParamDTO pageByKeyParamDTO) {
        pageByKeyParamDTO.setKey("%" + pageByKeyParamDTO.getKey() +"%");
        Integer totalCount = bookMapper.getGBookCount(pageByKeyParamDTO.getKey());

        PageBO pageBO = new PageBO(pageByKeyParamDTO);
        List<BookDTO> bookDTOS = bookMapper.listBookByKey(pageBO, pageByKeyParamDTO.getKey());

        PageResultDTO<BookDTO> resultDTO = PageResultDTO.instance(pageByKeyParamDTO, totalCount, bookDTOS);
        return ResponseDTO.succData(resultDTO);
    }
}
