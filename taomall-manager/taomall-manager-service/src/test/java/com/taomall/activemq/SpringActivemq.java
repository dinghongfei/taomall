package com.taomall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class SpringActivemq {

    @Test
    public void testJmsTemplate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-avtivemq.xml");
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        Destination destination = (Destination) context.getBean("test-queue");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                TextMessage messge = session.createTextMessage("spring activemq queue messge");
                return messge;
            }
        });
    }
}
