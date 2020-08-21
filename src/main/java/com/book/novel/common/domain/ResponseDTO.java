package com.book.novel.common.domain;

import com.book.novel.common.constant.ResponseCodeConst;
import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 响应数据传输DTO
 */

@Data
public class ResponseDTO<T> {

    protected Integer code;

    protected String msg;

    protected Boolean success;

    protected T data;

    public ResponseDTO() {
    }

    public ResponseDTO(ResponseCodeConst responseCodeConst, String msg) {
        this.code = responseCodeConst.getCode();
        this.msg = msg;
        this.success = responseCodeConst.isSuccess();
    }

    public ResponseDTO(ResponseCodeConst responseCodeConst, T data) {
        this.code = responseCodeConst.getCode();
        this.msg = responseCodeConst.getMsg();
        this.data = data;
        this.success = responseCodeConst.isSuccess();
    }

    public ResponseDTO(ResponseCodeConst responseCodeConst, T data, String msg) {
        this.code = responseCodeConst.getCode();
        this.msg = msg;
        this.data = data;
        this.success = responseCodeConst.isSuccess();
    }

    private ResponseDTO(ResponseCodeConst responseCodeConst) {
        this.code = responseCodeConst.getCode();
        this.msg = responseCodeConst.getMsg();
        this.success = responseCodeConst.isSuccess();
    }

    public static <T> ResponseDTO<T> succ() {
        return new ResponseDTO(ResponseCodeConst.SUCCESS);
    }

    public static <T> ResponseDTO<T> succData(T data, String msg) {
        return new ResponseDTO(ResponseCodeConst.SUCCESS, data, msg);
    }

    public static <T> ResponseDTO<T> succData(T data) {
        return new ResponseDTO(ResponseCodeConst.SUCCESS, data);
    }

    public static <T> ResponseDTO succMsg(String msg) {
        return new ResponseDTO(ResponseCodeConst.SUCCESS, msg);
    }


    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst) {
        return new ResponseDTO<>(codeConst);
    }

    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst, T t) {
        return new ResponseDTO<T>(codeConst, t);
    }

    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst, String msg) {
        return new ResponseDTO<T>(codeConst, msg);
    }
}
