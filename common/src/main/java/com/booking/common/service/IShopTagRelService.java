package com.booking.common.service;

import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IShopTagRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IShopTagRelService {

    List<ShopTagRelEntity> listAll();

    List<ShopTagRelEntity> listShopTagRel(ShopTagRelEntity shopTagRel);

    Page<List<ShopTagRelEntity>> listShopTagRelPage(ShopTagRelEntity shopTagRel, Integer pageNo, Integer pageSize);

    ShopTagRelEntity getShopTagRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int addShopTagRel(ShopTagRelEntity shopTagRelEntity);

    int addShopTagRel(List<ShopTagRelEntity> shopTagRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeShopTagRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeShopTagRel(List<String> ids);

    int updateById(ShopTagRelEntity shopTagRelEntity, String id);

    int update(ShopTagRelEntity shopTagRelEntity, ShopTagRelEntity where);

    List<ShopTagRelEntity> listShopTagRelTag(ShopTagRelEntity shopTagRelEntity);

}
