package com.booking.jobs.job;

import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.entity.OrderEntity;
import com.booking.common.mapper.OrderMapper;
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
public class CloseOrderJob extends AbstractJob{
    Logger logger = LoggerFactory.getLogger(CloseOrderJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("---执行定时任务 关闭订单 开始---");
        ApplicationContext applicationContext = null;
        try {
            applicationContext = getApplicationContext(jobExecutionContext);
            OrderEntity orderEntity = (OrderEntity) jobExecutionContext.getJobDetail().getJobDataMap().get("task");
            OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
            OrderEntity ret = orderMapper.selectOne(new OrderEntity(orderEntity.getId()));
            if(ret.getOrderStatus().equals(Constants.OrderStatus.PAID)){
                return;
            }
            orderEntity.setOrderStatus(Constants.OrderStatus.CANCELED);
            orderMapper.update(orderEntity, new OrderEntity(orderEntity.getId()));

            ICacheManager cacheManager = applicationContext.getBean(ICacheManager.class);
            cacheManager.remove(orderEntity.getOrderNo());
            logger.info("---执行定时任务 关闭订单 成功---");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("---执行定时任务 关闭订单 结束---");
    }

}
