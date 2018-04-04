package com.booking.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * ServiceUtil
 *
 * @author kai.liu
 * @date 2018/02/05
 */
public class ServiceUtil {
    Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    private static final SimpleDateFormat BIRTHDAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static boolean isAvatarFolderExist = false;


//    public static  String getValidateCodeKey(String businessCode, Phone phone) {
//        if (StringUtils.isEmpty(phone.getCountryCode())) {
//            phone.setCountryCode(Constants.CountryCode.CN);
//        }
//        return businessCode + phone.getCountryCode() + phone.getCellPhone();
//    }


    public static final String getAvatarPath() {
        String avatarPath = System.getProperties().getProperty("user.dir") + File.separator + "avatar" + File.separator;
        if (isAvatarFolderExist) {
            return avatarPath;
        } else {
            File path = new File(avatarPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            isAvatarFolderExist = true;
        }
        return avatarPath;
    }

    public static final String getAvatarPath(String avatarName) {
        String avatarPath = getAvatarPath() + avatarName;
        return avatarPath;
    }

    /**
     * 说 明: 生成验证码
     *
     * @param isOnlyNumber
     * @param length
     * @return
     */
    public static String createRandomString(boolean isOnlyNumber, int length) {
        String retStr = "";
        String strTable = isOnlyNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
//                ASCII码的'0'十进制位48，'9'为54
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += c;
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

}
