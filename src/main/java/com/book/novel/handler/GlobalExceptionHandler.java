package com.book.novel.handler;

import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 全局异常处理
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 添加全局异常处理流程
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseDTO exceptionHandler(Exception e) {
         log.error("error:", e);

        // http 请求方式错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResponseDTO.wrap(ResponseCodeConst.REQUEST_METHOD_ERROR);
        }

        // 参数类型错误
        if (e instanceof TypeMismatchException) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        // 数据转换时属性绑定错误
        if (e instanceof BindException) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        // json 格式错误
        if (e instanceof HttpMessageNotReadableException) {
            return ResponseDTO.wrap(ResponseCodeConst.JSON_FORMAT_ERROR);
        }

        // 参数校验未通过
        if (e instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            List<String> msgList = fieldErrors.stream().map(FieldError :: getDefaultMessage).collect(Collectors.toList());
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, String.join(",", msgList));
        }

        // 不支持的请求头
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return ResponseDTO.wrap(ResponseCodeConst.CONTENT_TYPE_ERROR);
        }

        // 连接超时
        if (e instanceof QueryTimeoutException) {
            return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_BUSY);
        }

        // 发送邮件出错
        if (e instanceof MailSendException) {
            return ResponseDTO.wrap(ResponseCodeConst.MAIL_ERROR);
        }

        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);
    }

}
