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
    public static class OrderType {
        public static final String NOW = "1";
        public static final String APPOINT = "2";
    }

    public static class ProductSaleStatus {
        public static final Integer ON_SALE = 1;
        public static final Integer SOLD_OUT = 2;
    }
}
