package com.booking.api.controllers;

import com.booking.api.AbstractTransactionalTestService;
import com.booking.common.base.ICacheManager;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IProductService;
import com.booking.common.utils.NetTool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestRedis extends AbstractTransactionalTestService {

    @Autowired
    ICacheManager cacheManager;

    @Autowired
    IOrderService orderService;

    @Test
    public void testSetRedis(){
        cacheManager.get("heihei");
    }

}