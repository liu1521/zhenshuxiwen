package com.book.novel.common.constant;

import lombok.Data;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 响应码常量
 */

@Data
public class ResponseCodeConst {

    public static final ResponseCodeConst SUCCESS = new ResponseCodeConst(1, "操作成功!", true);

    public static final ResponseCodeConst ERROR_PARAM = new ResponseCodeConst(101, "参数异常！");

    public static final ResponseCodeConst SYSTEM_ERROR = new ResponseCodeConst(111, "系统错误");

    public static final ResponseCodeConst SYSTEM_BUSY = new ResponseCodeConst(112, "系统繁忙,请稍后重试");

    public static final ResponseCodeConst NOT_EXISTS = new ResponseCodeConst(113, "数据不存在");

    public static final ResponseCodeConst REQUEST_METHOD_ERROR = new ResponseCodeConst(114, "请求方式错误");

    public static final ResponseCodeConst JSON_FORMAT_ERROR = new ResponseCodeConst(115, "JSON格式错误");

    public static final ResponseCodeConst CONTENT_TYPE_ERROR = new ResponseCodeConst(116, "不支持的contentType");

    public static final ResponseCodeConst MAIL_ERROR = new ResponseCodeConst(117, "邮箱错误");

    protected int code;

    protected String msg;

    protected boolean success;

    public ResponseCodeConst() {

    }

    protected ResponseCodeConst(int code, String msg) {
        this.code = code;
        this.msg = msg;
        success = false;
    }

    protected ResponseCodeConst(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

}
