package com.book.novel.module.eshop;

import com.book.novel.module.eshop.dto.*;
import com.book.novel.module.eshop.entity.HistoryAuthorBO;
import com.book.novel.module.eshop.entity.ShoppingCarEntity;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.novel.NovelMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 商品相关业务
 */

@Service
public class GoodsService {

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private NovelMapper novelMapper;

    public List<ShoppingCarDTO> listUnsettledByUserId(HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO userBO = loginTokenService.getUserTokenInfo(token);

        return goodsMapper.listUnsettledByUserId(userBO.getRequestUserId());
    }

    public boolean updateShoppingCar(ShoppingCarGoodsNumDTO goodsDTO, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO userBO = loginTokenService.getUserTokenInfo(token);


        Integer id = goodsMapper.getUnsettledShoppingCarId(userBO.getRequestUserId(), goodsDTO.getCid());
        if (Objects.nonNull(id)) {
            goodsMapper.updateNumById(id, goodsDTO.getNewNum());
        } else {
            ShoppingCarEntity shoppingCarEntity = ShoppingCarEntity.builder()
                    .userId(userBO.getRequestUserId())
                    .goodsId(goodsDTO.getCid())
                    .number(goodsDTO.getNewNum()).build();
            goodsMapper.save(shoppingCarEntity);
        }
        return true;
    }

    public List<GoodsDTO> listGoodsByQueryInfo(GoodsQueryDTO queryDTO) {
        return goodsMapper.listGoodsByQueryInfo(queryDTO);
    }

    public GoodsDetailDTO getGoodsDetailById(Integer cid) {
        return goodsMapper.getGoodsDetailById(cid);
    }

    public List<GoodsDTO> listGoodsByUserInfo(HttpServletRequest request) {
//        String token = loginTokenService.getToken(request);
//        RequestTokenBO userBO = loginTokenService.getUserTokenInfo(token);
        List<Integer> favoriteNovel = novelMapper.listNovelIdByUserId(1111);
        if (CollectionUtils.isNotEmpty(favoriteNovel)) {
            List<String> favoriteAuthor = novelMapper.listAuthorNameByNovelId(favoriteNovel);
            return goodsMapper.listFavoriteGoods(favoriteAuthor);
        }

        return null;
    }

    public List<HistoryAuthorDTO> listHistoryAuthor(Integer id){
        List<HistoryAuthorBO> historyAuthorBOS = goodsMapper.listHistoryAuthor(id);
        HashMap<String, HistoryAuthorDTO> hashMap = new HashMap<>();
        for (HistoryAuthorBO hb:historyAuthorBOS){
            HistoryAuthorDTO orDefault = hashMap.getOrDefault(hb.getUsername(),
                    new HistoryAuthorDTO(hb.getUsername(),0,new ArrayList<>()));
            orDefault.getChildren().add(new HistoryBookDTO(hb.getTitle(),hb.getValue()));
            orDefault.setValue(orDefault.getValue()+hb.getValue());
            hashMap.put(hb.getUsername(),orDefault);
        }
        return new ArrayList<>(hashMap.values());
    }

    public List<HistoryWeekDTO> listHistoryWeek(Integer uid){
        return goodsMapper.listHistoryWeek(uid);
    }

    List<HistoryCategoryDTO> listHistoryCategory(Integer uid){
        return goodsMapper.listHistoryCategory(uid);
    }
}
