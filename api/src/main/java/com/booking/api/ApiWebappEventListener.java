package com.booking.api;

import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ServletEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

/**
 * ApiWebappEventListener
 *
 * @author kai.liu
 * @date 2018/02/22
 */
public class ApiWebappEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(ApiWebappEventListener.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Context.putResourceMapping("/dist", "/dist");
        super.contextInitialized(servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
    }
}
