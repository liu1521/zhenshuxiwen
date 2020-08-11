package com.book.novel.module.chapter.constant;

import com.book.novel.common.constant.ResponseCodeConst;

/**
 * @Author: liu
 * @Date: 2020/8/11
 * @Description: 章节相关响应值常量
 */

public class ChapterResponseCodeConstant extends ResponseCodeConst {

    /**
     * 无效的章节id
     */
    public static final ChapterResponseCodeConstant CHAPTER_ID_VALID = new ChapterResponseCodeConstant(5001, "无效的章节id");

    /**
     * 无效的小说id或该小说作者还未发布章节!
     */
    public static final ChapterResponseCodeConstant NOVEL_ID_VALID = new ChapterResponseCodeConstant(5001, "无效的小说id或该小说作者还未发布章节!");

    public ChapterResponseCodeConstant(int code, String msg) {
        super(code, msg);
    }
}
