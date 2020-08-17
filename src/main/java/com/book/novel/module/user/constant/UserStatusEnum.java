package com.book.novel.module.user.constant;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: 用户状态常量
 */

public enum UserStatusEnum {

    /**
     * 用户正常状态 0
     */
    NORMAL(0, "正常"),

    /**
     * 注册作者等待审核 1
     */
    TO_BE_AUTHOR(1, "注册作者等待审核"),

    /**
     * 用户已被禁用 2
     */
    DISABLED(2, "禁用");

    private Integer value;

    private String desc;

    UserStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
