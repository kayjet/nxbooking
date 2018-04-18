package com.booking.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 *
 * @author kai.liu
 * @date 2018/04/02
 */
public class Constants {

    public static class OrderStatus {
        public static final String WAITING_PAY = "1";
        public static final String PAID = "2";
        public static final String CANCELED = "3";
    }

    public static class OrderPushStatus {
        public static final Integer NOT_PUSH = 1;
        public static final Integer PUSHED = 2;
    }

    public static class OrderPushHandlerStatus {
        public static final Integer NOT_HANDLERED = 1;
        public static final Integer HANDLERED = 2;
    }

    public static class OrderType {
        public static final String NOW = "1";
        public static final String APPOINT = "2";
    }

    public static class ProductSaleStatus {
        public static final Integer ON_SALE = 1;
        public static final Integer SOLD_OUT = 2;
    }

    public static class IsParentCode {
        public static final String YES = "T";
        public static final String NO = "F";
    }


    public static class WechatPayErrorCode {
        //        商户无此接口权限	商户未开通此接口权限	请商户前往申请此接口权限
        public static final String NOAUTH = "NOAUTH";
        //        余额不足	用户帐号余额不足	用户帐号余额不足，请用户充值或更换支付卡后再支付
        public static final String NOTENOUGH = "NOTENOUGH";
        //        商户订单已支付	商户订单已支付，无需重复操作	商户订单已支付，无需更多操作
        public static final String ORDERPAID = "ORDERPAID";
        //        订单已关闭	当前订单已关闭，无法支付	当前订单已关闭，请重新下单
        public static final String ORDERCLOSED = "ORDERCLOSED";
        //        系统错误	系统超时	系统异常，请用相同参数重新调用
        public static final String SYSTEMERROR = "SYSTEMERROR";
        //        商户订单号重复	同一笔交易不能多次提交	请核实商户订单号是否重复提交
        public static final String APPID_NOT_EXIST = "APPID_NOT_EXIST";
        public static final String APPID_MCHID_NOT_MATCH = "APPID_MCHID_NOT_MATCH";
        public static final String LACK_PARAMS = "LACK_PARAMS";
        public static final String OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";
        public static final String SIGNERROR = "SIGNERROR";
        public static final String XML_FORMAT_ERROR = "XML_FORMAT_ERROR";
        public static final String REQUIRE_POST_METHOD = "REQUIRE_POST_METHOD";
        public static final String POST_DATA_EMPTY = "POST_DATA_EMPTY";
        public static final String NOT_UTF8 = "NOT_UTF8";
        public static final String SUCCESS = "SUCCESS";


    }
}
