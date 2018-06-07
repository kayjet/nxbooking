package com.booking.jobs.controllers;

import com.alibaba.fastjson.JSON;
import com.booking.common.entity.OrderEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.quartz.MyQuartzExecutorDelegate;
import com.booking.common.utils.NetTool;
import com.booking.jobs.AbstractTransactionalTestService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class JobsControllerTest extends AbstractTransactionalTestService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    MyQuartzExecutorDelegate myQuartzExecutorDelegate;

    @org.junit.Test
    public void addJob() throws Exception {

//        OrderEntity orderEntity = orderMapper.selectOne(new OrderEntity("0b4c0cf4-c3e3-4414-a6d5-722576d7e7c9"));
        OrderEntity orderEntity = orderMapper.selectOne(new OrderEntity("1582c0f0-0abf-491c-9697-09141632590e"));
        myQuartzExecutorDelegate.addCloseOrderJob(orderEntity);
    }


    @org.junit.Test
    public void removeob() throws Exception {
        myQuartzExecutorDelegate.removeCloseOrderJob("1582c0f0-0abf-491c-9697-09141632590e");
    }

}