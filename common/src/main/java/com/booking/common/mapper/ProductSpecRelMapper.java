package com.booking.common.mapper;

import com.booking.common.entity.ProductSpecEntity;
import com.booking.common.entity.ProductSpecRelEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AuthorityMapper
 *
 * @author kai.liu
 * @date 2017/12/28
 */
public interface ProductSpecRelMapper extends IBaseMapper<ProductSpecRelEntity> {

    List<ProductSpecEntity> selectSpecList(@Param("pid") String pid);
}
