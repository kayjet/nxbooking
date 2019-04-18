package com.booking.api.controllers;

import com.booking.api.AbstractTransactionalTestService;
import com.booking.common.base.ICacheManager;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IProductService;
import com.booking.common.utils.NetTool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


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
//        String data ="[[{\"createTime\":1522723441000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"detail\":\"啊啊啊\",\"id\":\"1a2b10c3-8ab0-419e-b156-ead8b7cd87c0\",\"isOnSale\":0,\"pic\":\"\",\"price\":23,\"productSpecList\":[],\"relSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0201\",\"createTime\":1523456215000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"4\",\"name\":\"去冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456215000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0203\",\"createTime\":1523456220000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"6\",\"name\":\"常温\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456220000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0103\",\"createTime\":1523434844000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"9\",\"name\":\"无糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523434847000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"requestAddTagList\":[],\"requestSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true}]}],\"tagProductRelList\":[],\"title\":\"咖啡\",\"updateTime\":1522724557000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}],[{\"createTime\":1522723441000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"detail\":\"啊啊啊\",\"id\":\"1a2b10c3-8ab0-419e-b156-ead8b7cd87c0\",\"isOnSale\":0,\"pic\":\"\",\"price\":23,\"productSpecList\":[],\"relSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0201\",\"createTime\":1523456215000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"4\",\"name\":\"去冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456215000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0203\",\"createTime\":1523456220000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"6\",\"name\":\"常温\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456220000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0101\",\"createTime\":1523454671000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"7\",\"name\":\"半糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454671000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":true},{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false},{\"code\":\"0103\",\"createTime\":1523434844000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"9\",\"name\":\"无糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523434847000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"requestAddTagList\":[],\"requestSpecList\":[{\"parentCode\":\"02\",\"parentName\":\"冰度\",\"specList\":[{\"code\":\"0202\",\"createTime\":1523456217000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"5\",\"name\":\"少冰\",\"parentCode\":\"02\",\"parentId\":\"1\",\"parentName\":\"冰度\",\"updateTime\":1523456217000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]},{\"parentCode\":\"01\",\"parentName\":\"甜度\",\"specList\":[{\"code\":\"0102\",\"createTime\":1523454672000,\"createTimeEnd\":null,\"createTimeSearch\":\"\",\"createTimeStart\":null,\"id\":\"8\",\"name\":\"少糖\",\"parentCode\":\"01\",\"parentId\":\"2\",\"parentName\":\"甜度\",\"updateTime\":1523454672000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]}],\"tagProductRelList\":[],\"title\":\"咖啡\",\"updateTime\":1522724557000,\"updateTimeEnd\":null,\"updateTimeSearch\":\"\",\"updateTimeStart\":null,\"isActive\":false}]]";
        String data = "[\n" +
                "\n" +
                "\t[{\n" +
                "\t\t\"createTime\": 1522723441000,\n" +
                "\t\t\"createTimeEnd\": null,\n" +
                "\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\"createTimeStart\": null,\n" +
                "\t\t\"detail\": \"提拉米苏罐子蛋糕\",\n" +
                "\t\t\"id\": \"33b6658c-5fa1-4a6c-b4dd-9026dba6afc2\",\n" +
                "\t\t\"isOnSale\": 0,\n" +
                "\t\t\"pic\": \"\",\n" +
                "\t\t\"price\": 23,\n" +
                "\t\t\"productSpecList\": [],\n" +
                "\t\t\"relSpecList\": [{\n" +
                "\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\"specList\": [{\n" +
                "\t\t\t\t\"code\": \"0201\",\n" +
                "\t\t\t\t\"createTime\": 1523456215000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"4\",\n" +
                "\t\t\t\t\"name\": \"去冰\",\n" +
                "\t\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\t\"parentId\": \"1\",\n" +
                "\t\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\t\"updateTime\": 1523456215000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": true\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"code\": \"0202\",\n" +
                "\t\t\t\t\"createTime\": 1523456217000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"5\",\n" +
                "\t\t\t\t\"name\": \"少冰\",\n" +
                "\t\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\t\"parentId\": \"1\",\n" +
                "\t\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\t\"updateTime\": 1523456217000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"code\": \"0203\",\n" +
                "\t\t\t\t\"createTime\": 1523456220000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"6\",\n" +
                "\t\t\t\t\"name\": \"常温\",\n" +
                "\t\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\t\"parentId\": \"1\",\n" +
                "\t\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\t\"updateTime\": 1523456220000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}]\n" +
                "\t\t}, {\n" +
                "\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\"specList\": [{\n" +
                "\t\t\t\t\"code\": \"0101\",\n" +
                "\t\t\t\t\"createTime\": 1523454671000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"7\",\n" +
                "\t\t\t\t\"name\": \"半糖\",\n" +
                "\t\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\t\"parentId\": \"2\",\n" +
                "\t\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\t\"updateTime\": 1523454671000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": true\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"code\": \"0102\",\n" +
                "\t\t\t\t\"createTime\": 1523454672000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"8\",\n" +
                "\t\t\t\t\"name\": \"少糖\",\n" +
                "\t\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\t\"parentId\": \"2\",\n" +
                "\t\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\t\"updateTime\": 1523454672000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"code\": \"0103\",\n" +
                "\t\t\t\t\"createTime\": 1523434844000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"9\",\n" +
                "\t\t\t\t\"name\": \"无糖\",\n" +
                "\t\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\t\"parentId\": \"2\",\n" +
                "\t\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\t\"updateTime\": 1523434847000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}]\n" +
                "\t\t}],\n" +
                "\t\t\"requestAddTagList\": [],\n" +
                "\t\t\"requestSpecList\": [{\n" +
                "\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\"specList\": [{\n" +
                "\t\t\t\t\"code\": \"0202\",\n" +
                "\t\t\t\t\"createTime\": 1523456217000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"5\",\n" +
                "\t\t\t\t\"name\": \"少冰\",\n" +
                "\t\t\t\t\"parentCode\": \"02\",\n" +
                "\t\t\t\t\"parentId\": \"1\",\n" +
                "\t\t\t\t\"parentName\": \"冰度\",\n" +
                "\t\t\t\t\"updateTime\": 1523456217000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}]\n" +
                "\t\t}, {\n" +
                "\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\"specList\": [{\n" +
                "\t\t\t\t\"code\": \"0102\",\n" +
                "\t\t\t\t\"createTime\": 1523454672000,\n" +
                "\t\t\t\t\"createTimeEnd\": null,\n" +
                "\t\t\t\t\"createTimeSearch\": \"\",\n" +
                "\t\t\t\t\"createTimeStart\": null,\n" +
                "\t\t\t\t\"id\": \"8\",\n" +
                "\t\t\t\t\"name\": \"少糖\",\n" +
                "\t\t\t\t\"parentCode\": \"01\",\n" +
                "\t\t\t\t\"parentId\": \"2\",\n" +
                "\t\t\t\t\"parentName\": \"甜度\",\n" +
                "\t\t\t\t\"updateTime\": 1523454672000,\n" +
                "\t\t\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\t\t\"updateTimeStart\": null,\n" +
                "\t\t\t\t\"isActive\": false\n" +
                "\t\t\t}]\n" +
                "\t\t}],\n" +
                "\t\t\"tagProductRelList\": [],\n" +
                "\t\t\"title\": \"提拉米苏罐子蛋糕\",\n" +
                "\t\t\"updateTime\": 1522724557000,\n" +
                "\t\t\"updateTimeEnd\": null,\n" +
                "\t\t\"updateTimeSearch\": \"\",\n" +
                "\t\t\"updateTimeStart\": null,\n" +
                "\t\t\"isActive\": false\n" +
                "\t}]\n" +
                "]";
        byte[] result = NetTool.POST_JSON(url, data);
        System.out.println(result);
    }

    @Test
    public void payCallback() throws Exception {

        for (int i = 0;i < 100;i++){
            String xml = "<xml>\n" +
                    "  <appid><![CDATA[wxaac0edff2d9fe351]]></appid>\n" +
                    "  <attach><![CDATA[20180502162036,20180502163536]]></attach>\n" +
                    "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                    "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                    "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                    "  <mch_id><![CDATA[1499998882]]></mch_id>\n" +
                    "  <nonce_str><![CDATA[A45OFczOUxWxLyaU]]></nonce_str>\n" +
                    "  <openid><![CDATA[oCY6t4sqINgYL2JZ_VrRw-AwJzgU]]></openid>\n" +
                    "  <out_trade_no><![CDATA[WX1525249235957]]></out_trade_no>\n" +
                    "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <sign><![CDATA[C367BD208BDA5D848520780ACEC4627C]]></sign>\n" +
                    "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                    "  <total_fee>1</total_fee><coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                    "<coupon_count><![CDATA[1]]></coupon_count>\n" +
                    "<coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                    "<coupon_id><![CDATA[10000]]></coupon_id>\n" +
                    "<coupon_fee><![CDATA[100]]></coupon_fee>\n" +
                    "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                    "  <transaction_id><![CDATA[4200000067201804190940781232]]></transaction_id>\n" +
                    "</xml>";
            byte[] result = NetTool.POST_XML("http://localhost:9090/background/sp3/order/payCallback", xml);
            System.out.println(new String(result,"UTF-8"));
        }

    }

    @Autowired
    ICacheManager cacheManager;

    @Autowired
    IOrderService orderService;

    @Test
    public void testSetRedis() {
        cacheManager.set("heihei", orderService.getOrder("74b20f4d-aefa-49e7-baf1-7e46171d9803"), 5000);
    }

}