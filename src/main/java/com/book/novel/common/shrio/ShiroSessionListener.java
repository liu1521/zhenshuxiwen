package com.book.novel.common.shrio;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 统计Session数量
 */

public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }

    public AtomicInteger getSessionCount(){
        return sessionCount;
    }
}
