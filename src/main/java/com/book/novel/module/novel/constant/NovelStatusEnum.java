package com.book.novel.module.novel.constant;

/**
 * @Author: liu
 * @Date: 2020/9/10
 * @Description:
 */

public enum NovelStatusEnum {

    /**
     * 待审核状态 0
     */
    EXAMINE_WAIT(0, "待审核"),

    /**
     * 审核已通过 1
     */
    EXAMINE_SUCCESS(1, "审核通过"),

    /**
     * 审核已拒绝 2
     */
    EXAMINE_FAIL(2, "审核拒绝");

    private Integer value;

    private String desc;

    NovelStatusEnum(Integer value, String desc) {
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
