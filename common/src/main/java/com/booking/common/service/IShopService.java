package com.booking.common.service;

import com.booking.common.entity.ShopEntity;
import com.booking.common.entity.ShopTagRelForWebEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IShopService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IShopService {

    List<ShopEntity> listAll();

    List<ShopEntity> listShop(ShopEntity shop);

    ShopEntity getShop(String id);

    int addShop(ShopEntity shopEntity);

    int addShop(List<ShopEntity> shopEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeShop(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeShop(List<String> ids);

    int updateById(ShopEntity shopEntity, String id);

    int update(ShopEntity shopEntity, ShopEntity where);

    Page<List<ShopEntity>> listShopPage(ShopEntity shopEntity, Integer pageNo, Integer pageSize);

    List<ShopTagRelForWebEntity> listProducts(String shopId, boolean isCleanEmpty);

}
