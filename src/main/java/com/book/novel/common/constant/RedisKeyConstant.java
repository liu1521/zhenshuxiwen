package com.book.novel.common.constant;

/**
 * @Author: liu
 * @Date: 2020/8/12
 * @Description: redis关键词前缀常量
 */

public interface RedisKeyConstant {

    String USER_INFO_PREFIX = "user_info_prefix";

    String WAIT_ACTIVE_USER_PREFIX = "wait_active";

    String CATEGORY = "category";

    String RANK_HITS = "rank_hits";

    String RANK_DAY = "rank_hits_day";

    String RANK_WEEK = "rank_hits_week";

    String RANK_MONTH = "rank_hits_month";

    String RANK_RATING = "rank_rating";

    String RANK_FAVORITES = "rank_favorites";

    String RANK_RECOMMEND = "rank_recommend";

}
