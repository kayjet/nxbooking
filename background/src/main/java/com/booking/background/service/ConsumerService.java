package com.booking.background.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;

//import javax.jms.Destination;
//import javax.jms.JMSException;
//import javax.jms.TextMessage;

/**
 * ConsumerService
 *
 * @author kai.liu
 * @date 2018/04/07
 */
//@Service
public class ConsumerService {
    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    public TextMessage receive(Destination destination) {
//        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
//        try {
//            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
//                    + textMessage.getText());
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        return textMessage;
//    }
}
