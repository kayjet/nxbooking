package com.booking.jobs.controllers;

import com.booking.common.entity.OrderEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.jobs.job.CloseOrderJob;
import com.booking.jobs.service.MyQuartzExecutor;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.JSON;
import com.opdar.platform.annotations.Request;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * CommonController
 *
 * @author kai.liu
 * @date 2018/03/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class JobsController {
    private Logger logger = LoggerFactory.getLogger(JobsController.class);

    @Autowired
    private MyQuartzExecutor quartzExecutor;

    private static final String JOB_GROUP_NAME = "001";

    @Request(value = "/jobs/addJob", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Integer addJob(@JSON OrderEntity orderEntity) {
        logger.info("添加等待支付定时任务，orderNo=" + orderEntity.getOrderNo());
        DateTime startDate = new DateTime();
        return quartzExecutor.addJob(orderEntity.getOrderNo(), CloseOrderJob.class, orderEntity, startDate.plusMinutes(14).toDate(), JOB_GROUP_NAME, 0, 0);
    }

    @Request(value = "/jobs/removeJob", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Integer removeJob(String orderNo) {
        logger.info("删除等待支付定时任务，orderNo=" + orderNo);
        return quartzExecutor.closeJob(orderNo, JOB_GROUP_NAME);
    }


}
