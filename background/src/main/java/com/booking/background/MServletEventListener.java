package com.booking.background;

import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ServletEventListener;
import com.opdar.plugins.mybatis.core.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * MServletEventListener
 *
 * @author kai.liu
 * @date 2018/02/22
 */
public class MServletEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(MServletEventListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Context.putResourceMapping("/dist", "/dist");
        super.contextInitialized(servletContextEvent);

        ApplicationContext applicationContext = (ApplicationContext) servletContextEvent.getServletContext().getAttribute("applicationContext");

    }
}
