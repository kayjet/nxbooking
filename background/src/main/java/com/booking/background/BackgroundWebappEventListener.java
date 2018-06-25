package com.booking.background;

import com.booking.background.service.NotHandledOrderService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ServletEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * BackgroundWebappEventListener
 *
 * @author kai.liu
 * @date 2018/02/22
 */
public class BackgroundWebappEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(BackgroundWebappEventListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Context.putResourceMapping("/dist", "/dist");
        super.contextInitialized(servletContextEvent);
        ApplicationContext applicationContext = (ApplicationContext) servletContextEvent.getServletContext().getAttribute("applicationContext");
        NotHandledOrderService notHandledOrderService = applicationContext.getBean(NotHandledOrderService.class);
        notHandledOrderService.getAllShopNotHandledOrderList();
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

    }


}
