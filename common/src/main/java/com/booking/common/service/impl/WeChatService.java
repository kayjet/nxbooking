package com.booking.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.booking.common.utils.NetTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * WeChateService
 *
 * @author kai.liu
 * @date 2018/03/28
 */
@Service
public class WeChatService {
    Logger logger = LoggerFactory.getLogger(WeChatService.class);
    private static final String APP_ID = "wx8298196369027575";
    private static final String SECRET = "e05d3bd42ed70c526e16064daa751ef3";
    private static final String WX_REQ_URL = "https://api.weixin.qq.com/sns/jscode2session?" +
            "appid="+APP_ID+"&secret="+SECRET+"&js_code=%s&grant_type=authorization_code";

    public static class OpenidResult {
        private String session_key;
        private Long expires_in;
        private String openid;

        public String getSession_key() {
            return session_key;
        }

        public void setSession_key(String session_key) {
            this.session_key = session_key;
        }

        public Long getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(Long expires_in) {
            this.expires_in = expires_in;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }

    public OpenidResult getUserOpenId(String jsCode) {
        String url = String.format(WX_REQ_URL,jsCode);
        OpenidResult result= null;
        try {
            byte[] b = NetTool.GET(url);
            String ret = new String(b,"UTF-8");
            result =  JSON.parseObject(ret,OpenidResult.class);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        new WeChatService().getUserOpenId("061b5Y1e2vWOVD0YZG2e21zO1e2b5Y1D");
    }
}
