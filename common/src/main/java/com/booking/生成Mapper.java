package com.booking;

import com.booking.common.entity.UserEntity;
import com.booking.common.mapper.*;
import com.opdar.plugins.mybatis.core.ResultMapperParser;
import com.opdar.plugins.mybatis.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * 生成Mapper
 *
 * @author kai.liu
 * @date 2018/03/26
 */
public class 生成Mapper {
    Logger logger = LoggerFactory.getLogger(生成Mapper.class);

    public static void main(String[] args) {
        生成Mapper(OrderMapper.class);
    }

    private static void 生成Mapper(Class mapperClz) {
        String namespace = mapperClz.getName();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"utf-8\"?><!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"><mapper namespace=\"" + namespace + "\"></mapper>").getBytes());

        try {
            ResultMapperParser rmp = new ResultMapperParser();
            rmp.init("t_","booking");
            ByteArrayInputStream result = rmp.parse(inputStream);
            System.out.println(new String(Utils.is2byte(result)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
