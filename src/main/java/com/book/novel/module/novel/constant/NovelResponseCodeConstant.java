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

    public NovelResponseCodeConstant(int code, String msg) {
        super(code, msg);
    }


}
