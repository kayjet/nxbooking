package com.booking.jobs.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * AbstractJob
 *
 * @author kai.liu
 * @date 2018/04/19
 */
public abstract class AbstractJob implements Job {
    private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

    protected ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
        ApplicationContext appCtx = null;
        appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        if (appCtx == null) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }
}
