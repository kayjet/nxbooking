package com.booking.common.mapper;

import com.booking.common.entity.ProductEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AuthorityMapper
 *
 * @author kai.liu
 * @date 2017/12/28
 */
public interface ProductMapper extends IBaseMapper<ProductEntity> {

    List<ProductEntity> selectListByTagId(@Param("tagId") String tagId);

}
