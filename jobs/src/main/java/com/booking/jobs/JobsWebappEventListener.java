package com.booking.jobs;

import com.opdar.platform.core.base.ServletEventListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * JobsWebappEventListener
 *
 * @author kai.liu
 * @date 2018/06/07
 */
public class JobsWebappEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(JobsWebappEventListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("JobsWebappEventListener contextDestroyed");
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
