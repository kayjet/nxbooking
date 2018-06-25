package com.booking.api;

import com.opdar.plugins.mybatis.core.SqlSessionFactoryBean;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AbstractTransactionalTestService
 *
 * @author kai.liu
 * @date 2018/02/06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//@Transactional
public abstract class AbstractTransactionalTestService {
    protected Logger logger = LoggerFactory.getLogger(AbstractTransactionalTestService.class);

    @Autowired
    ApplicationContext applicationContext;

    @Before
    public void before(){
        SqlSessionFactoryBean sqlSessionFactoryBean =  applicationContext.getBean(SqlSessionFactoryBean.class);
        sqlSessionFactoryBean.setTypeHandlersPackage("com.ihygeia.vaccine.typehandler");
    }
}
