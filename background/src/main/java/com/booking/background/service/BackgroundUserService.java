package com.booking.background.service;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.background.interceptor.WSLoginSessionInterceptor;
import com.booking.common.base.ICacheManager;
import com.booking.common.entity.BackgroundUserEntity;
import com.booking.common.exceptions.ErrCodeException;
import com.booking.common.mapper.BackgroundUserMapper;
import com.booking.common.utils.EncryptUtil;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * BackgroundUserService
 *
 * @author kai.liu
 * @date 2018/04/20
 */
@Service
public class BackgroundUserService {
    Logger logger = LoggerFactory.getLogger(BackgroundUserService.class);

    @Autowired
    BackgroundUserMapper backgroundUserMapper;

    @Autowired
    ICacheManager cacheManager;

    public boolean login(String username, String password) {
        BackgroundUserEntity result = this.auth(username, password);
        if (result != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            result.setToken(token);
            Context.getRequest().getSession().setMaxInactiveInterval(-1);
            Context.getRequest().getSession().setAttribute(LoginSessionInterceptor.LOGIN_USER, result);
            return true;
        }
        return false;
    }

    public boolean wsLogin(String username, String password) {
        BackgroundUserEntity result = this.auth(username, password);
        if (result != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            result.setToken(token);
            Context.getRequest().getSession().setMaxInactiveInterval(-1);
            Context.getRequest().getSession().setAttribute(WSLoginSessionInterceptor.WS_LOGIN_USER, result);
            return true;
        }
        return false;
    }


    private BackgroundUserEntity auth(String username, String userPwd) {
        BackgroundUserEntity query = new BackgroundUserEntity();
        query.setUsername(username);
        //根据用户名查询密码盐
        BackgroundUserEntity user = backgroundUserMapper.selectOne(query);
        if (user == null || user.getSalt() == null) {
            return null;
        }
        String salt = user.getSalt();
        //根据盐计算正确密码
        String resultPwd = EncryptUtil.getEncryptPassword(userPwd.toUpperCase(), salt);
        query.setPassword(resultPwd);
        user = backgroundUserMapper.selectOne(query);
        if (user == null) {
            return null;
        }
        //去除密码信息并返回
        user.setPassword(null);
        return user;
    }

    public boolean resetPassword(String username, String newPwd) {
        return this.resetPassword(username, newPwd, null);
    }

    public boolean resetPassword(String username, String newPwd, String originPwd) {
        BackgroundUserEntity query = new BackgroundUserEntity();
        query.setUsername(username);
        BackgroundUserEntity updateUser = null;
        if (!StringUtils.isEmpty(originPwd)) {
            updateUser = this.auth(username, originPwd);
            if (updateUser == null) {
                throw new ErrCodeException(-1, "原密码不正确");
            }
        } else {
            updateUser = backgroundUserMapper.selectOne(query);
        }
        String newSalt = UUID.randomUUID().toString();
        String newPassword = EncryptUtil.getEncryptPassword(newPwd, newSalt);
        updateUser.setPassword(newPassword);
        updateUser.setSalt(newSalt);
        if (backgroundUserMapper.update(updateUser, query) <= 0) {
            throw new ErrCodeException(-1, "更新用户失败");
        }
        return true;
    }


    public BackgroundUserEntity register(String username, String password) {
        logger.info("开始注册 ");
        String uid = UUID.randomUUID().toString();
        String salt = UUID.randomUUID().toString();
        String resultPwd = EncryptUtil.getEncryptPassword(password.toUpperCase(), salt);
        Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());

        BackgroundUserEntity user = new BackgroundUserEntity();
        user.setUsername(username);
        user.setId(uid);
        user.setPassword(resultPwd);
        user.setSalt(salt);
        user.setCreateTime(currentTimeMillis);
        user.setUpdateTime(currentTimeMillis);
        if (backgroundUserMapper.insert(user) < 0) {
            throw new ErrCodeException(-1, "用户注册失败");
        }
        user.setPassword(null);
        logger.info("注册结束 ");
        return user;
    }
}
