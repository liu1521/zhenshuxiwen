package com.book.novel.module.eshop;

import com.book.novel.module.eshop.dto.*;
import com.book.novel.module.eshop.entity.HistoryAuthorBO;
import com.book.novel.module.eshop.entity.ShoppingCarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 商品相关Mapper
 */

@Mapper
@Component
public interface GoodsMapper {

    Integer getUnsettledShoppingCarId(Integer uid, Integer cid);

    void updateNumById(Integer id, Integer number);

    void save(ShoppingCarEntity shoppingCarEntity);

    List<ShoppingCarDTO> listUnsettledByUserId(Integer uid);

    List<GoodsDTO> listGoodsByQueryInfo(GoodsQueryDTO queryDTO);

    GoodsDetailDTO getGoodsDetailById(Integer cid);

    List<GoodsDTO> listFavoriteGoods(@Param("favoriteAuthor") List<String> favoriteAuthor);

    List<HistoryAuthorBO> listHistoryAuthor(@Param("id") Integer id);

    List<HistoryWeekDTO> listHistoryWeek(@Param("uid")Integer uid);

    List<HistoryCategoryDTO> listHistoryCategory(@Param("uid")Integer uid);
}
