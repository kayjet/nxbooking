package com.booking.common.dto;

import com.booking.common.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MakeOrderDto
 *
 * @author kai.liu
 * @date 2018/04/05
 */
public class MakeOrderDto {
    private OrderEntity orderEntity;
    private String paySign;
    private String timeStamp;//		是	时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
    private String  nonceStr;//	是	随机字符串，长度为32个字符以下。
    private String prepay_id;//		是	统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
    private String signType = "MD5";		//是	签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
