package com.booking.common.mapper;

import com.booking.common.entity.ShopTagRelEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;

import java.util.List;

/**
* AuthorityMapper
*
* @author kai.liu
* @date 2017/12/28
*/
public interface ShopTagRelMapper extends IBaseMapper<ShopTagRelEntity> {

    List<ShopTagRelEntity> selectJoinShopList(ShopTagRelEntity shopTagRelEntity);

    List<ShopTagRelEntity> selectJoinTagList(ShopTagRelEntity shopTagRelEntity);

}
