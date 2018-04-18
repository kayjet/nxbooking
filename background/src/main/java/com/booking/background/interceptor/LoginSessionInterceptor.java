package com.booking.background.interceptor;

import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * LoginSessionInterceptor
 *
 * @author kai.liu
 * @date 2018/04/18
 */
public class LoginSessionInterceptor implements Interceptor {
    Logger logger = LoggerFactory.getLogger(LoginSessionInterceptor.class);
    String LOGIN_USER = "LOGIN_USER";

    @Override
    public boolean before() {
        Object loginUser = Context.getRequest().getSession().getAttribute(LOGIN_USER);
        if (loginUser == null) {
            try {
                String contextPath = Context.getRequest().getContextPath();
                Context.getResponse().sendRedirect(contextPath + "/common/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean after() {
        return true;
    }

    @Override
    public void finish() {

    }
}
