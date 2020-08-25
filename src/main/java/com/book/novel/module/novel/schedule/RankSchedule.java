package com.book.novel.module.novel.schedule;

import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.module.novel.NovelService;
import com.book.novel.module.novel.constant.NovelHitsKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: liu
 * @Date: 2020/8/12
 * @Description: 定时更新排行榜
 */

@Configuration
@EnableScheduling
public class RankSchedule {

    @Autowired
    private NovelService novelService;

    /**
     * 每天 0 8 16点更新总点击量排行榜
     */
    @Scheduled(cron = "0 0 0/8 * * ?")
    public void updateNovelRankHits() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_HITS, 30);
    }

    /**
     * 每天4点更新日点击量排行榜
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void updateNovelRankHitsDay() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_DAY, 10);
        novelService.updateNovelHitsTo0(NovelHitsKeyConstant.HITS_DAY);
    }

    /**
     *每周一5点更新周点击量排行榜
     */
    @Scheduled(cron = "0 0 5 ? * 1")
    public void updateNovelRankHitsWeek() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_WEEK, 10);
        novelService.updateNovelHitsTo0(NovelHitsKeyConstant.HITS_WEEK);
    }

    /**
     * 每月一号6点更新月点击量排行榜
     */
    @Scheduled(cron = "0 0 6 1 * ?")
    public void updateNovelRankHitsMonth() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_MONTH, 10);
        novelService.updateNovelHitsTo0(NovelHitsKeyConstant.HITS_MONTH);
    }

    /**
     * 每天1 9 17点更新评分排行榜
     */
    @Scheduled(cron = "0 0 1/8 * * ?")
    public void updateNovelRankRating() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_RATING, 10);
    }

    /**
     * 每天2 10 18点更新收藏排行榜
     */
    @Scheduled(cron = "0 0 2/8 * * ?")
    public void updateNovelRankFavorites() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_FAVORITES, 10);
    }

    /**
     * 每天3 11 19点更新推荐票排行榜
     */
    @Scheduled(cron = "0 0 3/8 * * ?")
    public void updateNovelRankRecommend() {
        novelService.updateNovelRank(RedisKeyConstant.RANK_RECOMMEND, 10);
    }

//    @Scheduled(cron = "0/10 * * * * ?")
//    public void ha_ha() {
//        novelService.updateNovelRank(RedisKeyConstant.RANK_HITS, 30);
//    }

}
