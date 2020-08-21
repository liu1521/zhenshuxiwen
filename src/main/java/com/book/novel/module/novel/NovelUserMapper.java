package com.book.novel.module.novel;

import com.book.novel.common.domain.PageParamDTO;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.novel.bo.PageBO;
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

    List<NovelDTO> listNovelByCategory(@Param("pageBO") PageBO pageBO, @Param("categoryId") Integer categoryId);

    Integer getNovelCountByKey(@Param("key") String key);

    List<NovelDTO> listNovelByKey(@Param("pageBO") PageBO pageBO, @Param("key") String key);

    NovelDetailDTO getNovelDetailById(@Param("novelId") Integer novelId);

    List<NovelDTO> listRank(@Param("key") String key, @Param("num") Integer num);

    List<NovelDTO> listFavoritesNovel(@Param("userId") Integer requestUserId);

    List<NovelDTO> listAllNovel(@Param("pageBO") PageBO pageBO);

    void saveUNFavorites(@Param("novelId") Integer novelId, @Param("userId") Integer requestUserId);

    Integer removeUNFavorites(@Param("novelId") Integer novelId, @Param("userId") Integer requestUserId);

    List<NovelDetailDTO> listNovelDetailDTOByAuthorId(@Param("authorId") Integer requestUserId);

}
