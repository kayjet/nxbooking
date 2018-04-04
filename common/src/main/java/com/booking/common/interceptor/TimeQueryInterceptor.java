package com.booking.common.interceptor;

import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.DefaultInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * TimeQueryInterceptor
 *
 * @author kai.liu
 * @date 2018/03/26
 */
public class TimeQueryInterceptor extends DefaultInterceptor {
    Logger logger = LoggerFactory.getLogger(TimeQueryInterceptor.class);


    @Override
    public boolean preHandle() {
        try {
            Context.getRequest().setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (Context.getRequest().getParameterMap().containsKey("createTimeSearch")) {
            String[] times = (String[]) Context.getRequest().getParameterMap().get("createTimeSearch")[0].split(",");
            Timestamp startTime = Timestamp.valueOf(times[0]);
            Timestamp endTime = Timestamp.valueOf(times[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.DATE, 1);
            endTime = new Timestamp(calendar.getTimeInMillis());
            addParameter("createTimeStart", startTime);
            addParameter("createTimeEnd", endTime);
        }
        if (Context.getRequest().getParameterMap().containsKey("updateTimeSearch")) {
            String[] times = (String[]) Context.getRequest().getParameterMap().get("updateTimeSearch")[0].split(",");
            Timestamp startTime = Timestamp.valueOf(times[0]);
            Timestamp endTime = Timestamp.valueOf(times[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.DATE, 1);
            endTime = new Timestamp(calendar.getTimeInMillis());
            addParameter("updateTimeStart", startTime);
            addParameter("updateTimeEnd", endTime);
        }
        return true;
    }

    @Override
    public boolean postHandle() {
        return true;
    }

    @Override
    public void afterCompletion() {

    }
}
