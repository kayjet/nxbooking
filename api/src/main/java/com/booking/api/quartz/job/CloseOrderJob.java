package com.booking.api.quartz.job;

import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.entity.OrderEntity;
import com.booking.common.mapper.OrderMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * CloseOrderJob
 *
 * @author kai.liu
 * @date 2018/04/15
 */
public class CloseOrderJob implements Job {
    Logger logger = LoggerFactory.getLogger(CloseOrderJob.class);
    private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("---执行定时任务 关闭订单 开始---");
        ApplicationContext applicationContext = null;
        try {
            applicationContext = getApplicationContext(jobExecutionContext);
            OrderEntity orderEntity = (OrderEntity) jobExecutionContext.getJobDetail().getJobDataMap().get("task");
            orderEntity.setOrderStatus(Constants.OrderStatus.CANCELED);

            OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
            orderMapper.update(orderEntity, new OrderEntity(orderEntity.getId()));

            ICacheManager cacheManager = applicationContext.getBean(ICacheManager.class);
            cacheManager.remove(orderEntity.getOrderNo());
            logger.debug("---执行定时任务 关闭订单 成功---");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("---执行定时任务 关闭订单 结束---");
    }

    private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
        ApplicationContext appCtx = null;
        appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        if (appCtx == null) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }
}
