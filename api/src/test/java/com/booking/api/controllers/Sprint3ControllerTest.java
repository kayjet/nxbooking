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

import static org.junit.Assert.*;

public class Sprint3ControllerTest extends AbstractTransactionalTestService {

    @Autowired
    IProductService productService;

    @Test
    public void makeOrder() throws Exception {
        String userId = "22bcefc1-7879-47dc-a6d4-1f193a0b086f";
        String shopId = "9fe69327-b5d4-4597-999f-06ccae39356e";
        String concatPhone = "15801817832";
//        String shopId, String userId, String concatPhone, String totalPrice, @JSON List<List<ProductEntity>> products
        String url = "http://localhost:8080/api/sp3/order/makeOrder?shopId="+shopId+"&userId="+userId+"&concatPhone"+concatPhone+"&totalPrice=50";
        List<List<ProductEntity>> products = new ArrayList<List<ProductEntity>>();
        List<ProductEntity> cakes = productService.listProduct(new ProductEntity("05273e7c-5779-4e21-ba31-41f74e4780eb"));
        products.add(cakes);
        byte[] result = NetTool.POST(url, JSON.toJSON(products).toString());
        System.out.println(result);
    }

}