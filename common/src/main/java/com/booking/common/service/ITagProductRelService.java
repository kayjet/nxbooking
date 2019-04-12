package com.booking.common.service;

import com.booking.common.resp.Page;
import com.booking.common.entity.TagProductRelEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ITagProductRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface ITagProductRelService {

    List<TagProductRelEntity> listAll();

    List<TagProductRelEntity> listTagProductRel(TagProductRelEntity tagProductRel);

    Page<List<TagProductRelEntity>> listTagProductRelPage(TagProductRelEntity tagProductRel, Integer pageNo, Integer pageSize);

    TagProductRelEntity getTagProductRel(String id);

    int addTagProductRel(TagProductRelEntity tagProductRelEntity);

    int addTagProductRel(List<TagProductRelEntity> tagProductRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeTagProductRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeTagProductRel(List<String> ids);

    int updateById(TagProductRelEntity tagProductRelEntity, String id);

    int update(TagProductRelEntity tagProductRelEntity, TagProductRelEntity where);

    int delete(TagProductRelEntity tagProductRelEntity);
}
