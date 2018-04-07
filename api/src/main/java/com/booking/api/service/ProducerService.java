package com.booking.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * ProducerService
 *
 * @author kai.liu
 * @date 2018/04/07
 */
@Service
public class ProducerService {
    Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String msg) {
        System.out.println(Thread.currentThread().getName() + " 向队列" + destination.toString() + "发送消息---------------------->" + msg);
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    public void sendMessage(final String msg) {
        String destination = jmsTemplate.getDefaultDestinationName();
        System.out.println(Thread.currentThread().getName() + " 向队列" + destination + "发送消息---------------------->" + msg);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
