package com.book.novel.module.novel;

import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/10
 */

@Mapper
@Component
public interface NovelUserMapper {

    List<NovelDTO> listNovelByCategory(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("categoryId") Integer categoryId);

    int getNovelCountByKey(@Param("key") String key);

    List<NovelDTO> listNovelByKey(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("key") String key);

    NovelDetailDTO getNovelDetailById(@Param("novelId") Integer novelId);

    List<NovelDTO> getRank(@Param("key") String key, @Param("num") Integer num);

}
