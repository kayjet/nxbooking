package com.booking.common.service.impl;

import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.entity.TagProductRelEntity;
import com.booking.common.mapper.TagProductRelMapper;
import com.booking.common.service.ITagProductRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* TagProductRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class TagProductRelServiceImpl implements ITagProductRelService {
    Logger logger = LoggerFactory.getLogger(TagProductRelServiceImpl.class);

    @Autowired
    TagProductRelMapper tagProductRelMapper;

    @Override
    public List<TagProductRelEntity> listAll() {
        return tagProductRelMapper.selectList(null);
    }

    @Override
    public List<TagProductRelEntity> listTagProductRel(TagProductRelEntity tagProductRelEntity) {
        return tagProductRelMapper.selectList(tagProductRelEntity);
    }

    @Override
    public Page<List<TagProductRelEntity>> listTagProductRelPage(TagProductRelEntity tagProductRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  tagProductRelMapper.likeCount(tagProductRelEntity);
        Page<List<TagProductRelEntity>> page = new Page<List<TagProductRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<TagProductRelEntity> result =  tagProductRelMapper.selectLikeList(tagProductRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public TagProductRelEntity getTagProductRel(String id) {
        TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
        tagProductRelEntity.setId(id);
        return tagProductRelMapper.selectOne(tagProductRelEntity);
    }

    @Override
    public int addTagProductRel(TagProductRelEntity tagProductRelEntity) {
        tagProductRelEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        tagProductRelEntity.setCreateTime(ts);
        tagProductRelEntity.setUpdateTime(ts);
        return tagProductRelMapper.insert(tagProductRelEntity);
    }

    @Override
    public int addTagProductRel(List<TagProductRelEntity> tagProductRelEntitys) {
        int result = 0;
        for (TagProductRelEntity entity : tagProductRelEntitys) {
            result += this.addTagProductRel(entity);
        }
        return result;
    }

    @Override
    public int removeTagProductRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
        tagProductRelEntity.setId(id);
        return tagProductRelMapper.delete(tagProductRelEntity);
    }

    @Override
    public int removeTagProductRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
            tagProductRelEntity.setId(id);
            result += tagProductRelMapper.delete(tagProductRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(TagProductRelEntity tagProductRelEntity, String id) {
        TagProductRelEntity where = new TagProductRelEntity();
        where.setId(id);
        return this.update(tagProductRelEntity, where);
    }

    @Override
    public int update(TagProductRelEntity tagProductRelEntity, TagProductRelEntity where) {
        tagProductRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return tagProductRelMapper.update(tagProductRelEntity, where);
    }
}
