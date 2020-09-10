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
     * 无效token
     */
    public static final UserResponseCodeConst TOKEN_INVALID = new UserResponseCodeConst(3003, "无效token,请重新登录!");

    /**
     * 用户名或密码错误
     */
    public static final UserResponseCodeConst LOGIN_FAILED = new UserResponseCodeConst(3004, "用户名或密码错误!");

    /**
     * 您的账号已被禁用
     */
    public static final UserResponseCodeConst IS_DISABLED = new UserResponseCodeConst(3005, "您的账号已被禁用!");

    /**
     * 用户名已存在
     */
    public static final UserResponseCodeConst LOGIN_NAME_EXISTS = new UserResponseCodeConst(3006, "用户名已存在!");

    /**
     * 密码输入有误，请重新输入
     */
    public static final UserResponseCodeConst PASSWORD_ERROR = new UserResponseCodeConst(3007, "密码输入有误，请重新输入!");

    /**
     * 邮箱已被注册
     */
    public static final UserResponseCodeConst EMAIL_EXISTS = new UserResponseCodeConst(3008, "邮箱已被注册!");

    /**
     * 未授权用户
     */
    public static final UserResponseCodeConst UNAUTHENTICATED = new UserResponseCodeConst(3009, "您未被授权!");

    /**
     * 注册成功
     */
    public static final UserResponseCodeConst REGISTER_SUCCESS = new UserResponseCodeConst(3010, "注册成功!");

    /**
     * 验证码无效
     */
    public static final UserResponseCodeConst VERIFICATION_CODE_INVALID = new UserResponseCodeConst(3011, "验证码无效!");

    /**
     * 账号未激活
     */
    public static final UserResponseCodeConst NO_ACTIVE = new UserResponseCodeConst(3012, "账号未激活!");

    /**
     * 激活码无效
     */
    public static final UserResponseCodeConst ACTIVE_CODE_INVALID = new UserResponseCodeConst(3013, "激活码无效");

    /**
     * 图片错误
     */
    public static final UserResponseCodeConst IMG_FORMAT_ERROR = new UserResponseCodeConst(3014, "非法图片");

    /**
     * 已申请成为作家
     */
    public static final UserResponseCodeConst APPLIED = new UserResponseCodeConst(3015, "已申请,请等待管理员审核");

    /**
     * 推荐票不足
     */
    public static final UserResponseCodeConst RECOMMEND_ERROR = new UserResponseCodeConst(3016, "推荐票不足");

    /**
     * 激活成功
     */
    public static final UserResponseCodeConst ACTIVE_SUCCESS = new UserResponseCodeConst(3015, "激活成功");


    public UserResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
