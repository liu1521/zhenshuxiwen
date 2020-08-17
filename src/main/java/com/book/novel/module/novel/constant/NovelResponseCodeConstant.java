package com.book.novel.module.novel.constant;

import com.book.novel.common.constant.ResponseCodeConst;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: 小说信息相关响应码常量
 */
public class NovelResponseCodeConstant extends ResponseCodeConst {

    /**
     * 小说不存在
     */
    public static final NovelResponseCodeConstant NOVEL_ID_INVALID = new NovelResponseCodeConstant(4001, "小说不存在");

    /**
     * 小说类型不存在
     */
    public static final NovelResponseCodeConstant NOVEL_CATEGORY_ID_INVALID = new NovelResponseCodeConstant(4002, "小说类型不存在");

    /**
     * 获取排行榜出错
     */
    public static final NovelResponseCodeConstant RANK_ERROR = new NovelResponseCodeConstant(4003, "获取排行榜失败");

    /**
     * 收藏成功
     */
    public static final NovelResponseCodeConstant FAVORITES_SUCCESS = new NovelResponseCodeConstant(4004, "收藏成功");

    /**
     * 收藏失败
     */
    public static final NovelResponseCodeConstant FAVORITES_FAIL = new NovelResponseCodeConstant(4005, "已收藏小说");

    /**
     * 取消收藏成功
     */
    public static final NovelResponseCodeConstant UN_FAVORITES_SUCCESS = new NovelResponseCodeConstant(4006, "取消收藏成功");

    /**
     * 取消收藏失败
     */
    public static final NovelResponseCodeConstant UN_FAVORITES_FAIL = new NovelResponseCodeConstant(4007, "未收藏小说");

    public NovelResponseCodeConstant(int code, String msg) {
        super(code, msg);
    }


}
