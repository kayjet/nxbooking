package com.booking.api.controllers;

import com.alibaba.fastjson.JSON;
import com.booking.api.AbstractTransactionalTestService;
import com.booking.common.dto.ProductSpecDto;
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
//        String url = "http://localhost:8080/api/sp3/order/makeOrder?shopId=" + shopId + "&userId=" + userId + "&concatPhone" + concatPhone + "&totalPrice=50";
        String url = "http://localhost:8080/api/sp3/order/makeOrder?shopId=9fe69327-b5d4-4597-999f-06ccae39356e&userId=f37224c8-11b7-4f76-aead-457e0992e8b2&concatPhone=12346&totalPrice=66&orderType=1&orderTime=15:28";
        //        List<List<ProductEntity>> products = new ArrayList<List<ProductEntity>>();
//        List<ProductEntity> cakes = productService.listProduct(new ProductEntity("05273e7c-5779-4e21-ba31-41f74e4780eb"));
//        List<ProductSpecDto> requestSpecList = new ArrayList<ProductSpecDto>();
//        requestSpecList.add();
//        cakes.get(0).getRequestSpecList();
//        products.add(cakes);
        String data ="[[{\"createTime\":1522723441000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"detail\":\"啊啊啊\",\"id\":\"1a2b10c3-8ab0-419e-b156-ead8b7cd87c0\",\"isOnSale\":0,\"pic\":\"\",\"price\":23,\"productSpecList\":[],\"relSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0201\",\"createTime\":1523456215000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"4\",\"name\":\"去冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456215000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0203\",\"createTime\":1523456220000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"6\",\"name\":\"常温\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456220000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0103\",\"createTime\":1523434844000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"9\",\"name\":\"无糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523434847000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"requestAddTagList\":[],\"requestSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true}]}],\"tagProductRelList\":[],\"title\":\"咖啡\",\"updateTime\":1522724557000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}],[{\"createTime\":1522723441000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"detail\":\"啊啊啊\",\"id\":\"1a2b10c3-8ab0-419e-b156-ead8b7cd87c0\",\"isOnSale\":0,\"pic\":\"\",\"price\":23,\"productSpecList\":[],\"relSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0201\",\"createTime\":1523456215000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"4\",\"name\":\"去冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456215000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0203\",\"createTime\":1523456220000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"6\",\"name\":\"常温\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456220000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0103\",\"createTime\":1523434844000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"9\",\"name\":\"无糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523434847000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"requestAddTagList\":[],\"requestSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"tagProductRelList\":[],\"title\":\"咖啡\",\"updateTime\":1522724557000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]]";
        byte[] result = NetTool.POST_JSON(url, data);
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