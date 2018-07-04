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
import java.util.*;

/**
 * CommonService
 *
 * @author kai.liu
 * @date 2018/01/31
 */
@Service
public class CommonService {
    private String userAgreementUrl = "http://www.baidu.com";

    public String uploadAvatar(FileItem[] file) throws IOException {
        return this.uploadAvatar(null, file);
    }

    public String uploadAvatar(String userId, FileItem[] files) throws IOException {
        String avatarName = "";
        for (FileItem item : files) {
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

    public Set<String> listAllAvatarNames() throws IOException {
        String avatarPath = ServiceUtil.getAvatarPath();
        File avatars = new File(avatarPath);
        File[] files = avatars.listFiles();
        Set<String> names = new HashSet<String>();
        for (File f : files) {
            names.add(f.getName());
        }
        return names;
    }

    public Boolean deleteImage(String imageName) throws IOException {
        String avatarPath = ServiceUtil.getAvatarPath();
        File avatars = new File(avatarPath + imageName);
        if (avatars.exists() && avatars.isFile()) {
            return avatars.delete();
        }
        return false;
    }


//    public void deleteValidateCode(Phone phone, String businessCode) {
//        String sKey = ServiceUtil.getValidateCodeKey(businessCode, phone);
//        cacheManager.remove(sKey);
//    }

    public String getUserAgreementUrl() {
        return userAgreementUrl;
    }

}
