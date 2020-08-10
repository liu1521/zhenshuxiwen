package com.book.novel.config;

import com.book.novel.interceptor.ZhenShuXiWenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: liu
 * @Date: 2020/8/10
 * @Description: web相关配置类
 */

@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ZhenShuXiWenInterceptor interceptor;

    // 注册过滤器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
