package com.booking.common.quartz;


import com.alibaba.fastjson.JSON;
import com.booking.common.entity.OrderEntity;
import com.booking.common.utils.NetTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${jobs.add.url}")
    private String addJobUrl;

    @Value("${jobs.remove.url}")
    private String removeJobUrl;

    /**
     * 添加一个倒计时任务
     *
     * @return
     */
    public Object addCloseOrderJob(OrderEntity orderEntity) throws Exception {
        logger.info("QuartzExecutorDelegate 添加等待支付定时任务，orderNo=" + orderEntity.getOrderNo());
        return NetTool.POST_JSON(addJobUrl, JSON.toJSONString(orderEntity));
    }

    /**
     * 删除一个倒计时任务
     *
     * @return
     */
    public Object removeCloseOrderJob(String orderNo) throws Exception {
        logger.info("QuartzExecutorDelegate 删除等待支付定时任务，orderNo=" + orderNo);
        return NetTool.GET(removeJobUrl + "?orderNo=" + orderNo);
    }
//
//    /**
//     * 从数据库加载还未执行的任务（spring容器初始化的时候会自动加载）
//     *
//     * @return
//     */
//    public Object resumeCloseOrderJob(OrderEntity orderEntity) {
//        quartzExecutor.resumeJob();
//        return "OK";
//    }
}
