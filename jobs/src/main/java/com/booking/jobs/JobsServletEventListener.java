package com.booking.jobs;

import com.alibaba.druid.pool.DruidDataSource;
import com.opdar.platform.core.base.ServletEventListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.servlet.ServletContextEvent;

/**
 * JobsServletEventListener
 *
 * @author kai.liu
 * @date 2018/06/07
 */
public class JobsServletEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(JobsServletEventListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("JobsServletEventListener contextDestroyed");
        ApplicationContext applicationContext = (ApplicationContext) servletContextEvent.getServletContext().getAttribute("applicationContext");
        Scheduler scheduler = (Scheduler) applicationContext.getBean("quartzScheduler");
        try {
            if(scheduler.isStarted()){
                scheduler.shutdown(false);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        super.contextDestroyed(servletContextEvent);
    }
}
