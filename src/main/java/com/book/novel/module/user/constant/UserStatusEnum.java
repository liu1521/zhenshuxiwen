package com.book.novel.module.user.constant;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description:
 */

public enum UserStatusEnum {

    /**
     * 用户未激活 0
     */
    NOT_ACTIVE(0, "禁用"),

    /**
     * 用户正常状态 1
     */
    NORMAL(1, "正常"),

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
