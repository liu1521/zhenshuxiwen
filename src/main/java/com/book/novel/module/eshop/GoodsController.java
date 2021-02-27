package com.book.novel.module.eshop;

import com.book.novel.common.anno.NeedUser;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.eshop.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2021/2/23
 * @Description: 商品相关接口
 */

@Api(tags = "商品相关接口")
@RestController
@ApiImplicitParams({@ApiImplicitParam(name = "x-access-token", value = "x-access-token", required = false, paramType = "header",dataType = "string",dataTypeClass = String.class)})
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation("获取用户购物车信息")
    @GetMapping("/api/eshop/shoppingcar")
    @NeedUser
    public ResponseDTO<List<ShoppingCarDTO>> listShoppingCar(HttpServletRequest request){
        List<ShoppingCarDTO> shoppingCarDTOList = goodsService.listUnsettledByUserId(request);
        return ResponseDTO.succData(shoppingCarDTOList);
    }

    @ApiOperation("修改用户购物车信息")
    @PostMapping("/api/eshop/shoppingcar")
    @NeedUser
    public ResponseDTO<List<GoodsDTO>> updateShoppingCar(@RequestBody ShoppingCarGoodsNumDTO goodsDTO, HttpServletRequest request){
        boolean updated = goodsService.updateShoppingCar(goodsDTO, request);
        if (updated) {
            return ResponseDTO.succData(null);
        } else {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }
    }

    @ApiOperation("获取指定商品信息")
    @PostMapping("/api/eshop/goods")
    @NoNeedLogin
    public ResponseDTO<List<GoodsDTO>> listGoodsByQueryInfo(@RequestBody GoodsQueryDTO queryDTO){
        List<GoodsDTO> goodsDTOList = goodsService.listGoodsByQueryInfo(queryDTO);
        return ResponseDTO.succData(goodsDTOList);
    }

    @ApiOperation("根据id获取指定商品信息")
    @GetMapping("/api/eshop/goods/{cid}")
    @NoNeedLogin
    public ResponseDTO<GoodsDetailDTO> getGoodsDetailById(@PathVariable("cid") Integer cid) {
        return ResponseDTO.succData(goodsService.getGoodsDetailById(cid));
    }

    @ApiOperation("推荐商品")
    @GetMapping("/api/eshop/goods")
    @NoNeedLogin
    public ResponseDTO<List<GoodsDTO>> listGoodsByUserInfo(HttpServletRequest request){
        List<GoodsDTO> goodsDTOList = goodsService.listGoodsByUserInfo(request);
        return ResponseDTO.succData(goodsDTOList);
    }

    @ApiOperation("获取浏览数-作者")
    @GetMapping("/api/history/author/{uid}")
    @NoNeedLogin
    public ResponseDTO<List<HistoryAuthorDTO>> listHistoryAuthor(@PathVariable Integer uid) {
        List<HistoryAuthorDTO> historyAuthorDTOS = goodsService.listHistoryAuthor(uid);
        return ResponseDTO.succData(historyAuthorDTOS);
    }
    @ApiOperation("获取最近7天每日阅读量")
    @GetMapping("/api/history/week/{uid}")
    @NoNeedLogin
    public ResponseDTO<List<HistoryWeekDTO>> listHistoryWeek(@PathVariable Integer uid) {
        return ResponseDTO.succData(goodsService.listHistoryWeek(uid));
    }
    @ApiOperation("获取每种小说类型阅读量")
    @GetMapping("/api/history/category/{uid}")
    @NoNeedLogin
    public ResponseDTO<List<HistoryCategoryDTO>> listHistoryCategory(@PathVariable Integer uid) {
        return ResponseDTO.succData(goodsService.listHistoryCategory(uid));
    }


}
