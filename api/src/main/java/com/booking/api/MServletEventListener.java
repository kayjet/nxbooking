package com.booking.api;

import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ServletEventListener;
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
//        SqlSessionFactoryBean sqlSessionFactoryBean =  applicationContext.getBean(SqlSessionFactoryBean.class);
//        sqlSessionFactoryBean.setTypeHandlersPackage("com.ihygeia.vaccine.typehandler");
//        sqlSessionFactoryBean.getConfiguration().getTypeHandlerRegistry().register(NullableStringTypeHandler.class);
    }
}
