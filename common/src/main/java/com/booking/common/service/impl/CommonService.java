package com.booking.common.service.impl;

import com.booking.common.mapper.UserMapper;
import com.booking.common.utils.NetTool;
import com.booking.common.utils.ServiceUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * CommonService
 *
 * @author kai.liu
 * @date 2018/01/31
 */
@Service
public class CommonService {
    Logger logger = LoggerFactory.getLogger(CommonService.class);

//    @Autowired
//    ICacheManager cacheManager;

    @Autowired
    private UserMapper userMapper;


    private int intValidateCodeLen = 6;
    private long longValidateCodeExpiredTime = 300000L;
    private String userAgreementUrl = "http://www.baidu.com";


//    public Map<String, Object> getValidateCode(final Phone phone, final String businessCode) {
//        Map<String, Object> result = new HashMap<String, Object>(3);
//        String validateCode = ServiceUtil.createRandomString(true, intValidateCodeLen);
//        String sKey = ServiceUtil.getValidateCodeKey(businessCode, phone);
//        String countKey = sKey + "_count";
//        Long count = (Long) cacheManager.get(countKey);
//        if (cacheManager.get(sKey) != null && count != null && count>0 && System.currentTimeMillis() - count<60000 ) {
//            result.put("ret", new Boolean(false));
//            result.put("msg", "已经发送验证码");
//            return result;
//        }
//        String msg = "您的验证码为" + validateCode;
//        ShortMessageEntity shortMessageEntity = new ShortMessageEntity();
//        shortMessageEntity.setTid(UUID.randomUUID().toString());
//        shortMessageEntity.setMobile(phone.getCellPhone());
//        shortMessageEntity.setDetail(msg);
//        shortMessageEntity.setMsgType(MsgType.SHORT_MSG);
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        shortMessageEntity.setCreateTime(ts);
//        shortMessageEntity.setUpdateTime(ts);
//        shortMessageRecordMapper.insert(shortMessageEntity);
//
//        cacheManager.set(sKey, validateCode, longValidateCodeExpiredTime);
//        cacheManager.set(countKey, System.currentTimeMillis(), 60000);
//        logger.info("手机号为" + phone.getCellPhone() + "验证码为" + validateCode);
//        result.put("validateCode", validateCode);
//        result.put("ret", new Boolean(true));
//        result.put("msg", "发送成功");
//        return result;
//    }

//    public boolean doCheckValidateCode(Phone phone, String validateCode, String businessCode) {
//        logger.info("doCheckValidateCode 开始验证 手机号为" + phone.getCellPhone() + "验证码为" + validateCode);
//        String sKey = ServiceUtil.getValidateCodeKey(businessCode, phone);
//        String cachedValidateCode = (String) cacheManager.get(sKey);
//        if (validateCode.equals(cachedValidateCode)) {
//            logger.info("doCheckValidateCode 验证通过");
//            return true;
//        }
//        logger.info("doCheckValidateCode 验证失败");
//        return false;
//    }

    public String uploadAvatar(FileItem[] file) throws IOException {
        return this.uploadAvatar(null, file);
    }

    public String uploadAvatar(String userId, FileItem[] file) throws IOException {
        String avatarName = "";
        if (file.length > 0) {
            FileItem item = file[0];
            String avatarPath = ServiceUtil.getAvatarPath();
            avatarName = (UUID.randomUUID().toString().replaceAll("-", "")) + item.getName();
            FileCopyUtils.copy(item.getInputStream(), new FileOutputStream(new File(avatarPath + avatarName)));
        }
        return avatarName;
    }

    public byte[] getAvatar(String avatarName) throws IOException {
        String avatarPath = ServiceUtil.getAvatarPath(avatarName);
        File avatar = new File(avatarPath);
        if (avatar.exists()) {
            return NetTool.read(new FileInputStream(avatar));
        }
        return null;
    }

//    public void deleteValidateCode(Phone phone, String businessCode) {
//        String sKey = ServiceUtil.getValidateCodeKey(businessCode, phone);
//        cacheManager.remove(sKey);
//    }

    public String getUserAgreementUrl() {
        return userAgreementUrl;
    }

}
