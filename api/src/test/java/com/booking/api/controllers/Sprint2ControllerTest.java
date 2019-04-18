package com.booking.api.controllers;

import com.booking.api.AbstractTransactionalTestService;
import com.booking.common.service.IShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Sprint2ControllerTest extends AbstractTransactionalTestService {

    @Autowired
    IShopService shopService;

    @Test
    public void testListProduct() {
        String shopId = "9e00dff3-70ed-47ba-a41f-47594a3ff45e";
        shopService.listProducts(shopId, true);
    }
}
