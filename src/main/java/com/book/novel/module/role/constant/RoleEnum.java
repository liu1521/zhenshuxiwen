package com.book.novel.module.role.constant;

/**
 * @Author: liu
 * @Date: 2020/8/9
 * @Description: 角色信息常量
 */

public enum RoleEnum {
    /**
     * 普通用户
     */
    USER(6666, "user"),

    /**
     * 作者
     */
    AUTHOR(8888, "author"),

    /**
     * 管理员
     */
    ADMIN(10086, "admin");

    private Integer value;

    private String desc;

    RoleEnum(Integer value, String desc) {
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
