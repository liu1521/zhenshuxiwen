package com.book.novel;

import com.alibaba.fastjson.JSONObject;
import com.book.novel.module.mail.MailService;
import com.book.novel.module.user.UserMapper;
import com.book.novel.module.user.constant.UserSexEnum;
import com.book.novel.module.user.entity.UserEntity;
import com.book.novel.module.user.vo.UserRegisterFormVO;
import com.book.novel.util.BeanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class NovelApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private ValueOperations<String, String> valueOperations;


    @Test
    void contextLoads() {
    }

    @Test
    public void userMapperTest() {
        System.out.println(userMapper.getUserById(6666));
    }

    @Test
    public void testEmail() {
        System.out.println(mailService.sendSimpleMail("173531102@qq.com", "subject", "content"));
    }

    @Test
    public void jsonTest() {
        UserRegisterFormVO userRegisterFormVO = new UserRegisterFormVO();
        userRegisterFormVO.setCode("code");
        userRegisterFormVO.setCodeUuid("codeUuid");
        userRegisterFormVO.setEmail("123@qq.com");
        userRegisterFormVO.setPassword("hhh");
        userRegisterFormVO.setSex("男");
        System.out.println(userRegisterFormVO);
        String json = (String) JSONObject.toJSON(userRegisterFormVO);
        valueOperations.set("1", json, 60L, TimeUnit.SECONDS);
        UserRegisterFormVO urfv = (UserRegisterFormVO) JSONObject.parse(valueOperations.get("1"));
        System.out.println(urfv);
    }

    @Test
    public void localDateTimeTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ct = now.plusDays(-1);
        Duration duration = Duration.between(ct, now);
        System.out.println(duration.toMinutes());
    }

}
