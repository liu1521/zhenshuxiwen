package com.book.novel;

import com.book.novel.module.novel.NovelService;
import com.book.novel.module.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NovelApplicationTests {
    @Test
    void contextLoads() {
    }

    @Autowired
    NovelService novelService;

    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
//        Integer tickets = userMapper.getTicketsById(6666);
//        Integer ticketsById = userMapper.getTicketsById(2);
//        System.out.println(tickets);
//        System.out.println(ticketsById);
//
//        System.out.println(ticketsById < 1);
    }

}
