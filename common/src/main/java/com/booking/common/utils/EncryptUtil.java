package com.booking.common.utils;

import com.opdar.platform.utils.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EncryptUtil
 *
 * @author kai.liu
 * @date 2018/03/28
 */
public class EncryptUtil {
    Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    public static String getEncryptPassword(String userPwd, String salt) {
        return SHA1.encrypt(userPwd.concat(salt));
    }
}
