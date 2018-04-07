package com.booking.api.controllers;

import com.alibaba.fastjson.JSON;
import com.booking.api.AbstractTransactionalTestService;
import com.booking.common.entity.ProductEntity;
import com.booking.common.service.IProductService;
import com.booking.common.utils.NetTool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Sprint3ControllerTest extends AbstractTransactionalTestService {

    @Autowired
    IProductService productService;

    @Test
    public void makeOrder() throws Exception {
        String userId = "f37224c8-11b7-4f76-aead-457e0992e8b2";
        String shopId = "9fe69327-b5d4-4597-999f-06ccae39356e";
        String concatPhone = "15801817832";
//        String shopId, String userId, String concatPhone, String totalPrice, @JSON List<List<ProductEntity>> products
        String url = "http://localhost:8080/api/sp3/order/makeOrder?shopId=" + shopId + "&userId=" + userId + "&concatPhone" + concatPhone + "&totalPrice=50";
        List<List<ProductEntity>> products = new ArrayList<List<ProductEntity>>();
        List<ProductEntity> cakes = productService.listProduct(new ProductEntity("05273e7c-5779-4e21-ba31-41f74e4780eb"));
        products.add(cakes);
        byte[] result = NetTool.POST_JSON(url, JSON.toJSON(products).toString());
        System.out.println(result);
    }

    @Test
    public void payCallback() throws Exception {
        String xml = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee><coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                "<coupon_count><![CDATA[1]]></coupon_count>\n" +
                "<coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                "<coupon_id><![CDATA[10000]]></coupon_id>\n" +
                "<coupon_fee><![CDATA[100]]></coupon_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";
        NetTool.POST_XML("http://localhost:8080/api/sp3/order/payCallback", xml);
    }

}