package com.booking.common.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.dto.ProductListDto;
import com.booking.common.mapper.OrderMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * AuthorityEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = OrderMapper.class)
public class OrderEntity implements Serializable{
    private String id;
    @Excel(name = "订单号", height = 20, width = 40, isImportField = "true_st")
    private String orderNo;
    private String orderStatus;
    @Excel(name = "总价", height = 20, width = 40)
    private Double totalPrice;
    private Double totalPriceFromWeb;
    private String orderTime;
    private String orderType;
    private String concatPhone;
    @Excel(name = "微信订单号", height = 20, width = 40)
    private String transactionId;
    private Integer lockVersion;
    @Sort(type = Sort.SortType.DESC)
    @Excel(name = "创建时间", height = 20, width = 40, databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer isPushed;
    private Integer isHandler;

    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private Integer hasBeenHandle;

    @Excel(name = "费率", height = 20, width = 40)
    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private Double fee;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and create_time >= {}", plain = false)
    private Timestamp createTimeStart;
    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and create_time <= {}", plain = false)
    private Timestamp createTimeEnd;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and update_time >= {}", plain = false)
    private Timestamp updateTimeStart;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and update_time <= {}", plain = false)
    private Timestamp updateTimeEnd;

    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private String createTimeSearch;

    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private String updateTimeSearch;

    @Field(resultmap = false,
            insert = false,
            update = false,
            delete = false,
            select = true)
    private String shopId;

    @Field(resultmap = false,
            insert = false,
            update = false,
            delete = false,
            select = false)
    private  List<OrderDetailDto> orderDetailList;


    @Field(resultmap = false,
            insert = false,
            update = false,
            delete = false,
            select = false)
    private  ProductListDto productListDto;



    public OrderEntity() {
    }

    public OrderEntity(String id) {
        this.id = id;
    }



    public ProductListDto getProductListDto() {
        return productListDto;
    }

    public void setProductListDto(ProductListDto productListDto) {
        this.productListDto = productListDto;
    }

    public List<OrderDetailDto> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDto> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Integer getIsPushed() {
        return isPushed;
    }

    public void setIsPushed(Integer isPushed) {
        this.isPushed = isPushed;
    }

    public Integer getIsHandler() {
        return isHandler;
    }

    public void setIsHandler(Integer isHandler) {
        this.isHandler = isHandler;
    }

    public Integer getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }

    public Integer getHasBeenHandle() {
        return hasBeenHandle;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setHasBeenHandle(Integer hasBeenHandle) {
        this.hasBeenHandle = hasBeenHandle;
    }

    public Timestamp getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Timestamp createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Timestamp getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Timestamp createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Timestamp getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Timestamp updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public Timestamp getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public String getCreateTimeSearch() {
        return createTimeSearch;
    }

    public void setCreateTimeSearch(String createTimeSearch) {
        this.createTimeSearch = createTimeSearch;
    }

    public String getUpdateTimeSearch() {
        return updateTimeSearch;
    }

    public void setUpdateTimeSearch(String updateTimeSearch) {
        this.updateTimeSearch = updateTimeSearch;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getTotalPriceFromWeb() {
        return totalPriceFromWeb;
    }

    public void setTotalPriceFromWeb(Double totalPriceFromWeb) {
        this.totalPriceFromWeb = totalPriceFromWeb;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getConcatPhone() {
        return concatPhone;
    }

    public void setConcatPhone(String concatPhone) {
        this.concatPhone = concatPhone;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (!getId().equals(that.getId())) return false;
        return getOrderNo().equals(that.getOrderNo());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getOrderNo().hashCode();
        return result;
    }
}
