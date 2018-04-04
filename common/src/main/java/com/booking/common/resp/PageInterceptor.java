package com.booking.common.resp;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by shiju on 2016/12/27.
 */
@Intercepts({@Signature(
        type= StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class})})
public class PageInterceptor implements Interceptor {
    private final static ThreadLocal<Integer> start = new ThreadLocal<Integer>();
    private final static ThreadLocal<Integer> limit = new ThreadLocal<Integer>();
    private Field field = ReflectionUtils.findField(BoundSql.class, "sql");

    public PageInterceptor() {
        field.setAccessible(true);
    }

    public static void set(int start, int limit){
        PageInterceptor.start.set(start);
        PageInterceptor.limit.set(limit);
    }

    public static void setPage(int pageNo,int pageSize){
        int start = 0;
        if(pageNo > 0){
            start = (pageNo-1) * pageSize;
        }
        PageInterceptor.start.set(start);
        PageInterceptor.limit.set(pageSize);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try{
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            if(handler instanceof RoutingStatementHandler){
                BoundSql boundSql = handler.getBoundSql();
                String page = "";
                if(start.get() != null){
                    page = start.get()+",";
                }
                if(limit.get() != null){
                    page += limit.get();
                }
                if(page.length() > 0){
                    ReflectionUtils.setField(field,boundSql,boundSql.getSql().concat(" limit ").concat(page));
                }
            }
            return invocation.proceed();
        }finally {
            start.remove();
            limit.remove();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
