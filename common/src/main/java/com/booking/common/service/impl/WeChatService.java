package com.booking.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.booking.common.dto.WechatPayResultDto;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderWechatEntity;
import com.booking.common.entity.WechatPayCallbackEntity;
import com.booking.common.mapper.OrderWechatMapper;
import com.booking.common.mapper.WechatPayCallbackMapper;
import com.booking.common.utils.NetTool;
import com.booking.common.utils.WxPayUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * WeChateService
 *
 * @author kai.liu
 * @date 2018/03/28
 */
@Service
public class WeChatService {
    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);
    private static final String APP_ID = "wxaac0edff2d9fe351";
    private static final String MCH_ID = "1499998882";
    private static final String KEY = "11111111111111111111111111111111";
    private static final String SECRET = "61123cb58879ee86cec4c67d13b815aa";
    private static final String WX_REQ_URL = "https://api.weixin.qq.com/sns/jscode2session?" +
            "appid=" + APP_ID + "&secret=" + SECRET + "&js_code=%s&grant_type=authorization_code";
    private static final String WX_MAKE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private WechatPayCallbackMapper wechatPayCallbackMapper;

    @Value("${wechat.callback}")
    private String wechatCallbackUrl;

    @Value("${wechat.isdebug}")
    private boolean wechatIsDebug;

    @Value("${wechat.order.ip}")
    private String orderIp;

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
        String url = String.format(WX_REQ_URL, jsCode);
        OpenidResult result = null;
        try {
            byte[] b = NetTool.GET(url);
            String ret = new String(b, "UTF-8");
            result = JSON.parseObject(ret, OpenidResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String createSmallAppPaySign(String prepay_id, long currentTimeMillis, String nonceStr) {
        logger.info("创建小程序paySign start");
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appId", APP_ID);
        map.put("timeStamp", currentTimeMillis + "");
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=" + prepay_id);
        map.put("signType", "MD5");
        String sign = WxPayUtil.createSign(KEY, "UTF-8", map);
        logger.info("创建小程序paySign end");
        return sign;
    }

    public boolean validateSign(WechatPayCallbackEntity wechatPayCallbackEntity) {
        logger.info("验证回调sign start");
        SortedMap<String, String> map = new TreeMap<String, String>();
        if (!StringUtils.isEmpty(wechatPayCallbackEntity.getAttach())) {
            String[] ret = wechatPayCallbackEntity.getAttach().split(",");
            String time_start = ret[0];
            String time_expire = ret[1];
            map.put("time_start", time_start);
            map.put("time_expire", time_expire);
            map.put("attach", wechatPayCallbackEntity.getAttach());
        }
        map.put("appid", APP_ID);
        map.put("mch_id", MCH_ID);
        map.put("spbill_create_ip", orderIp);
        map.put("notify_url", wechatCallbackUrl);
        map.put("fee_type", "CNY");
        map.put("trade_type", "JSAPI");
        map.put("nonce_str", wechatPayCallbackEntity.getNonce_str());
        map.put("out_trade_no", wechatPayCallbackEntity.getOut_trade_no());
        map.put("total_fee", wechatPayCallbackEntity.getTotal_fee());
        map.put("openid", wechatPayCallbackEntity.getOpenid());
        String sign = WxPayUtil.createSign(KEY, "UTF-8", map);
        boolean result = false;
        if (sign.equalsIgnoreCase(wechatPayCallbackEntity.getSign())) {
            result = true;
        }
        logger.info("验证回调sign end ，结果为 result = " + result);
        return result;
    }

    public WechatPayResultDto prepareSmallAppOrder(OrderEntity result, String openId) {
        logger.info("创建微信订单 start");
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", APP_ID);
        map.put("mch_id", MCH_ID);
        map.put("nonce_str", WxPayUtil.createNoncestr());
        try {
            map.put("body", new String("暖系-饮品".getBytes("UTF-8"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("out_trade_no", result.getOrderNo());
        map.put("fee_type", "CNY");
        //单位：分
        if (wechatIsDebug) {
            map.put("total_fee", String.valueOf(1));
        } else {
            int price = (int) (result.getTotalPrice() * 10 * 10);
            map.put("total_fee", String.valueOf(price));
        }
        map.put("spbill_create_ip", orderIp);
        DateTime now = new DateTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time_start = dateFormat.format(now.toDate());
        String time_expire = dateFormat.format(now.plusMinutes(15).toDate());
        map.put("time_start", time_start);
        map.put("time_expire", time_expire);
        map.put("notify_url", wechatCallbackUrl);
        map.put("trade_type", "JSAPI");
        map.put("openid", openId);
        map.put("attach", time_start + "," + time_expire);
        String sign = WxPayUtil.createSign(KEY, "UTF-8", map);
        map.put("sign", sign);
        String requestData = WxPayUtil.getRequestXml(map);
        byte[] ret = NetTool.POST_XML(WX_MAKE_ORDER_URL, requestData);
        XmlMapper mapper = new XmlMapper();
        WechatPayResultDto wechatPayResultDto = null;
        try {
            String prepareSmallAppOrder = new String(ret, "UTF-8");
            logger.info("--------- prepareSmallAppOrder ------------------------");
            logger.info(prepareSmallAppOrder);
            logger.info("--------- prepareSmallAppOrder ------------------------");
            wechatPayResultDto = mapper.readValue(prepareSmallAppOrder, WechatPayResultDto.class);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        logger.info("创建微信订单 end");
        return wechatPayResultDto;
    }

    public void savePayCallbackResult(WechatPayCallbackEntity wechatPayCallbackEntity) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            wechatPayCallbackEntity.setCreateTime(ts);
            wechatPayCallbackEntity.setUpdateTime(ts);
            wechatPayCallbackEntity.setId(UUID.randomUUID().toString());
            wechatPayCallbackMapper.insert(wechatPayCallbackEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
