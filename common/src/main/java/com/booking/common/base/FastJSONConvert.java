package com.booking.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opdar.platform.core.convert.JSONConvert;

import java.lang.reflect.Type;

/**
 * Created by shiju on 2018/1/11.
 */
public class FastJSONConvert extends JSONConvert {
    @Override
    public Object convert(Object o, Type type) throws Exception {
        if (o != null && o instanceof byte[] && ((byte[]) o).length > 0) {
            return JSON.parseObject(new String((byte[]) o,"UTF-8"), type);
        } else {
            return null;
        }
    }

    @Override
    public byte[] serialization(Object o) throws Exception {
//        int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingToString, true);
        return JSON.toJSONBytes(o,SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse);
    }
}
