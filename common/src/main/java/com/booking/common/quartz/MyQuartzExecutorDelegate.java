package com.booking.common.quartz;

import com.booking.common.quartz.job.CloseOrderJob;
import com.booking.common.entity.OrderEntity;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MyQuartzExecutorDelegate
 *
 * @author kai.liu
 * @date 2018/04/15
 */
@Component
public class MyQuartzExecutorDelegate {
    Logger logger = LoggerFactory.getLogger(MyQuartzExecutorDelegate.class);
    private static final String JOB_GROUP_NAME = "001";

    @Autowired
    MyQuartzExecutor quartzExecutor;

    /**
     * 添加一个倒计时任务
     *
     * @return
     */
    public Object addCloseOrderJob(OrderEntity orderEntity) {
        logger.info("添加等待支付定时任务，orderNo=" + orderEntity.getOrderNo());
        DateTime startDate = new DateTime();
        return quartzExecutor.addJob(orderEntity.getOrderNo(), CloseOrderJob.class, orderEntity, startDate.plusMinutes(14).toDate(),
                JOB_GROUP_NAME, 0, 0);
    }

    /**
     * 删除一个倒计时任务
     *
     * @return
     */
    public Object removeCloseOrderJob(String orderNo) {
        logger.info("删除等待支付定时任务，orderNo=" + orderNo);
        return quartzExecutor.closeJob(orderNo, JOB_GROUP_NAME);
    }

    /**
     * 从数据库加载还未执行的任务（spring容器初始化的时候会自动加载）
     *
     * @return
     */
    public Object resumeCloseOrderJob(OrderEntity orderEntity) {
        quartzExecutor.resumeJob();
        return "OK";
    }
}
