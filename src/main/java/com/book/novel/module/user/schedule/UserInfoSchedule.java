package com.book.novel.module.user.schedule;

import com.book.novel.module.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: liu
 * @Date: 2020/9/9
 * @Description:
 */

@Configuration
@EnableScheduling
public class UserInfoSchedule {

    @Autowired
    private UserMapper userMapper;

    /**
     * 每日0点增加用户的推荐票
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRecommendAddOne() {
        userMapper.updateRecommendAddOne();
    }

}
