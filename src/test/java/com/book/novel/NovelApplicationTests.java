package com.book.novel;

import com.book.novel.module.chapter.ChapterService;
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

    @Autowired
    ChapterService chapterService;

    @Test
    public void test() {

    }

}
