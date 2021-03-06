package com.booking.common.mapper;

import com.booking.common.entity.ProductSpecEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AuthorityMapper
 *
 * @author kai.liu
 * @date 2017/12/28
 */
public interface ProductSpecMapper extends IBaseMapper<ProductSpecEntity> {

    List<ProductSpecEntity> selectParentSpec();

    String selectMaxParentCode();

    String selectMaxChildCode(@Param("parentCode") String parentCode);

}

