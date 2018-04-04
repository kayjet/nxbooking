package com.booking.common.dto;

import com.booking.common.entity.ProductEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * ProductDto
 *
 * @author kai.liu
 * @date 2018/04/04
 */
public class ProductListDto {
    private Integer sum;
    private List<ProductDto> productDtos;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }
}
