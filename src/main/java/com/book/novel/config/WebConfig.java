package com.book.novel.config;

import com.book.novel.interceptor.ZhenShuXiWenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

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

    /**
     * 配置过滤器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    /**
     * 配置文件上传管理
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.ofMegabytes(5L));
        multipartConfigFactory.setMaxRequestSize(DataSize.ofMegabytes(10L));

        return multipartConfigFactory.createMultipartConfig();
    }
}
