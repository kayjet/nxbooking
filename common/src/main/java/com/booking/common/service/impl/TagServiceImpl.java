package com.booking.common.service.impl;

import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.entity.TagEntity;
import com.booking.common.mapper.TagMapper;
import com.booking.common.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * TagServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class TagServiceImpl implements ITagService {
    Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<TagEntity> listAll() {
        return tagMapper.selectList(null);
    }

    @Override
    public List<TagEntity> listTag(TagEntity tagEntity) {
        return tagMapper.selectList(tagEntity);
    }

    @Override
    public Page<List<TagEntity>> listTagPage(TagEntity tagEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = tagMapper.likeCount(tagEntity);
        Page<List<TagEntity>> page = new Page<List<TagEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<TagEntity> result = tagMapper.selectLikeList(tagEntity);
        page.setResult(result);
        return page;
    }

    @Override
    public TagEntity getTag(String id) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(id);
        return tagMapper.selectOne(tagEntity);
    }

    @Override
    public TagEntity addTag(TagEntity tagEntity) {
        tagEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        tagEntity.setCreateTime(ts);
        tagEntity.setUpdateTime(ts);
        tagMapper.insert(tagEntity);
        return tagEntity;
    }

    @Override
    public int addTag(List<TagEntity> tagEntitys) {
        int result = 0;
        for (TagEntity entity : tagEntitys) {
            entity.setId(UUID.randomUUID().toString());
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            entity.setCreateTime(ts);
            entity.setUpdateTime(ts);
            result += tagMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeTag(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(id);
        return tagMapper.delete(tagEntity);
    }

    @Override
    public int removeTag(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            TagEntity tagEntity = new TagEntity();
            tagEntity.setId(id);
            result += tagMapper.delete(tagEntity);
        }
        return result;
    }

    @Override
    public int updateById(TagEntity tagEntity, String id) {
        TagEntity where = new TagEntity();
        where.setId(id);
        return this.update(tagEntity, where);
    }

    @Override
    public int update(TagEntity tagEntity, TagEntity where) {
        tagEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return tagMapper.update(tagEntity, where);
    }
}
