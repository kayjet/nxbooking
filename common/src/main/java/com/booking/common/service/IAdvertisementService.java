package com.booking.common.service;

import com.booking.common.entity.AdvertisementEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IAdvertisementService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IAdvertisementService {

    List<AdvertisementEntity> listAll();

    List<AdvertisementEntity> listAdvertisement(AdvertisementEntity advertisement);

    Page<List<AdvertisementEntity>> listAdvertisementPage(AdvertisementEntity advertisement, Integer pageNo, Integer pageSize);

    AdvertisementEntity getAdvertisement(String id);

    int addAdvertisement(AdvertisementEntity advertisementEntity);

    int addAdvertisement(List<AdvertisementEntity> advertisementEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeAdvertisement(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeAdvertisement(List<String> ids);

    int updateById(AdvertisementEntity advertisementEntity, String id);

    int update(AdvertisementEntity advertisementEntity, AdvertisementEntity where);
}
