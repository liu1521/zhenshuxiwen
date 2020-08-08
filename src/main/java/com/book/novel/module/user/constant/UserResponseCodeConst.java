package com.book.novel.module.user.constant;

import com.book.novel.common.constant.ResponseCodeConst;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户相关响应码常量
 */
public class UserResponseCodeConst extends ResponseCodeConst {

    /**
     * 用户不存在
     */
    public static final UserResponseCodeConst USER_NOT_EXISTS = new UserResponseCodeConst(3001, "用户不存在！");

    /**
     * 更新用户信息失败
     */
    public static final UserResponseCodeConst UPDATE_FAILED = new UserResponseCodeConst(3002, "用户更新失败！");

    /**
     * 用户名或密码错误
     */
    public static final UserResponseCodeConst LOGIN_FAILED = new UserResponseCodeConst(3004, "用户名或密码错误!");

    /**
     * 您的账号已被禁用，不得登录系统
     */
    public static final UserResponseCodeConst IS_DISABLED = new UserResponseCodeConst(3005, "您的账号已被禁用，不得登录系统!");

    /**
     * 登录名已存在
     */
    public static final UserResponseCodeConst LOGIN_NAME_EXISTS = new UserResponseCodeConst(3006, "登录名已存在!");

    /**
     * 密码输入有误，请重新输入
     */
    public static final UserResponseCodeConst PASSWORD_ERROR = new UserResponseCodeConst(3007, "密码输入有误，请重新输入");

    /**
     * 邮箱已经注册
     */
    public static final UserResponseCodeConst EMAIL_EXISTS = new UserResponseCodeConst(3008, "邮箱已经注册");

    /**
     * 验证码无效
     */
    public static final UserResponseCodeConst VERIFICATION_CODE_INVALID = new UserResponseCodeConst(3011, "验证码无效");

    public UserResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
