package com.booking.common.service.impl;

import com.booking.common.entity.AdvertisementEntity;
import com.booking.common.mapper.AdvertisementMapper;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.service.IAdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* AdvertisementServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class AdvertisementServiceImpl implements IAdvertisementService {
    Logger logger = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    @Autowired
    AdvertisementMapper advertisementMapper;

    @Override
    public List<AdvertisementEntity> listAll() {
        return advertisementMapper.selectList(null);
    }

    @Override
    public List<AdvertisementEntity> listAdvertisement(AdvertisementEntity advertisementEntity) {
        return advertisementMapper.selectList(advertisementEntity);
    }

    @Override
    public Page<List<AdvertisementEntity>> listAdvertisementPage(AdvertisementEntity advertisementEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  advertisementMapper.likeCount(advertisementEntity);
        Page<List<AdvertisementEntity>> page = new Page<List<AdvertisementEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<AdvertisementEntity> result =  advertisementMapper.selectLikeList(advertisementEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public AdvertisementEntity getAdvertisement(String id) {
        AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        advertisementEntity.setId(id);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        advertisementEntity.setCreateTime(ts);
        advertisementEntity.setUpdateTime(ts);
        return advertisementMapper.selectOne(advertisementEntity);
    }

    @Override
    public int addAdvertisement(AdvertisementEntity advertisementEntity) {
        advertisementEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        advertisementEntity.setCreateTime(ts);
        advertisementEntity.setUpdateTime(ts);
        return advertisementMapper.insert(advertisementEntity);
    }

    @Override
    public int addAdvertisement(List<AdvertisementEntity> advertisementEntitys) {
        int result = 0;
        for (AdvertisementEntity entity : advertisementEntitys) {
            result += advertisementMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeAdvertisement(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        advertisementEntity.setId(id);
        return advertisementMapper.delete(advertisementEntity);
    }

    @Override
    public int removeAdvertisement(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            AdvertisementEntity advertisementEntity = new AdvertisementEntity();
            advertisementEntity.setId(id);
            result += advertisementMapper.delete(advertisementEntity);
        }
        return result;
    }

    @Override
    public int updateById(AdvertisementEntity advertisementEntity, String id) {
        AdvertisementEntity where = new AdvertisementEntity();
        where.setId(id);
        return this.update(advertisementEntity, where);
    }

    @Override
    public int update(AdvertisementEntity advertisementEntity, AdvertisementEntity where) {
        advertisementEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return advertisementMapper.update(advertisementEntity, where);
    }
}
