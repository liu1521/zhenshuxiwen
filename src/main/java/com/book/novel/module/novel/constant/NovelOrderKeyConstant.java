package com.book.novel.module.novel.constant;

import com.book.novel.common.constant.OrderKeyConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/14
 * @Description: 小说排序关键词
 */
public interface NovelOrderKeyConstant extends OrderKeyConstant {

    String UPDATE_TIME = "update_time";

    String HITS = "hits";

    String RATING = "rating";

    String FAVORITES = "favorites";

    String HITS_DAY = "hits_day";

    String HITS_WEEK = "hits_week";

    String HITS_MONTH = "hits_month";

    String RECOMMEND = "recommend";

    static List<String> orderKeyList() {
        List<String> orderKeyList = new ArrayList<>();
        orderKeyList.add(UPDATE_TIME);
        orderKeyList.add(HITS);
        orderKeyList.add(RATING);
        orderKeyList.add(FAVORITES);
        orderKeyList.add(HITS_DAY);
        orderKeyList.add(HITS_WEEK);
        orderKeyList.add(HITS_MONTH);
        orderKeyList.add(RECOMMEND);
        return orderKeyList;
    }
}
