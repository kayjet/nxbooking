package com.booking.common.service;

import com.booking.common.entity.TagEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ITagService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface ITagService {

    List<TagEntity> listAll();

    List<TagEntity> listTag(TagEntity tag);

    Page<List<TagEntity>> listTagPage(TagEntity tag, Integer pageNo, Integer pageSize);

    TagEntity getTag(String id);

    TagEntity addTag(TagEntity tagEntity);

    int addTag(List<TagEntity> tagEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeTag(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeTag(List<String> ids);

    @Transactional(rollbackFor = Throwable.class)
    int updateById(TagEntity tagEntity, String id);

    int update(TagEntity tagEntity, TagEntity where);
}
