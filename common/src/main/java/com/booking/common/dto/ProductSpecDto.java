package com.booking.common.dto;

import com.booking.common.entity.ProductSpecEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductSpecDto
 *
 * @author kai.liu
 * @date 2018/04/12
 */
public class ProductSpecDto {
    private String parentName;
    private String parentCode;
    private List<ProductSpecEntity> specList = new ArrayList<ProductSpecEntity>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<ProductSpecEntity> getSpecList() {
        return specList;
    }

    public void setSpecList(List<ProductSpecEntity> specList) {
        this.specList = specList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSpecDto that = (ProductSpecDto) o;

        if (getParentName() != null ? !getParentName().equals(that.getParentName()) : that.getParentName() != null)
            return false;
        return getParentCode() != null ? getParentCode().equals(that.getParentCode()) : that.getParentCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getParentName() != null ? getParentName().hashCode() : 0;
        result = 31 * result + (getParentCode() != null ? getParentCode().hashCode() : 0);
        return result;
    }
}
