package com.book.novel.module.book;

import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.domain.PageByKeyParamDTO;
import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.PageResultDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.book.dto.BookDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: liu
 * @Date: 2020/11/10
 * @Description:
 */

@Api(tags = "在线商城实体书相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header", dataType = "string", dataTypeClass = String.class)})
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "分页查询所有实体书信息")
    @PostMapping("/api/book/listAll")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<BookDTO>> listBookByPage(@Valid @RequestBody PageParamDTO pageParamDTO) {
        return bookService.listBookByPage(pageParamDTO);
    }

    @ApiOperation(value = "根据id查询实体书信息")
    @PostMapping("/api/book/getBookById")
    @NoNeedLogin
    public ResponseDTO<BookDTO> getBookById(@RequestParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @ApiOperation(value = "根据关键字搜索实体书")
    @PostMapping("/api/book/listBookByKey")
    @NoNeedLogin
    public ResponseDTO<PageResultDTO<BookDTO>> listBookByKey(@Valid @RequestBody PageByKeyParamDTO pageByKeyParamDTO) {
        return bookService.listBookByKey(pageByKeyParamDTO);
    }
}
