package com.taomall.pagehelper;

import com.github.pagehelper.PageHelper;
import com.taomall.dao.ItemMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;

/**
 *
 *
 */
public class PageHelperTest {

    @Test
    public void testQueueProducer() throws Exception {
        //创建连接工厂对象ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //创建连接对象connection
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session对象，
        // 第一个参数是是否开启事务，它是个分布式事务，会很慢，所以一般不使用事务。保证数据的最终一致
        // 如果第一个参数为true，第二个参数自动忽略，如果不开启事务false，第二个参数为消息的应答模式，一般自动应答就ok
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个Destination对象，两种形式queue，topic，参数就是消息队列的名称
        Queue queue = session.createQueue("test-queue");
        //使用session对象创建一个producer对象
        MessageProducer producer = session.createProducer(queue);
        //创建一个TextMessage对象
        //TextMessage textMessage = new ActiveMQTextMessage();
        //textMessage.setText("hello activemq");
        TextMessage textMessage = session.createTextMessage("hello activemq");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testQueueConsumer() throws Exception {
        //创建连接工厂对象ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //创建连接对象connection
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session对象，
        // 第一个参数是是否开启事务，它是个分布式事务，会很慢，所以一般不使用事务。保证数据的最终一致
        // 如果第一个参数为true，第二个参数自动忽略，如果不开启事务false，第二个参数为消息的应答模式，一般自动应答就ok
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个Destination对象，两种形式queue，topic，参数就是消息队列的名称
        Queue queue = session.createQueue("test-queue");
        //使用session对象创建一个consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(m -> {
            //取消息的内容
            if (m instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) m;
                try {
                    String text = textMessage.getText();
                    //打印消息
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //系统等待接收消息
        /*while (true){
            Thread.sleep(1000);
        }*/
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testTopicProducer() throws Exception{
        //创建连接工厂对象ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //创建连接对象connection
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session对象，
        // 第一个参数是是否开启事务，它是个分布式事务，会很慢，所以一般不使用事务。保证数据的最终一致
        // 如果第一个参数为true，第二个参数自动忽略，如果不开启事务false，第二个参数为消息的应答模式，一般自动应答就ok
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个Destination对象，两种形式queue，topic，参数就是消息队列的名称
        Topic topic = session.createTopic("test-topic");
        //使用session对象创建一个producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个TextMessage对象
        //TextMessage textMessage = new ActiveMQTextMessage();
        //textMessage.setText("hello activemq");
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }



    @Test
    public void testTopicConsumer() throws Exception {
        //创建连接工厂对象ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //创建连接对象connection
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session对象，
        // 第一个参数是是否开启事务，它是个分布式事务，会很慢，所以一般不使用事务。保证数据的最终一致
        // 如果第一个参数为true，第二个参数自动忽略，如果不开启事务false，第二个参数为消息的应答模式，一般自动应答就ok
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个Destination对象，两种形式queue，topic，参数就是消息队列的名称
        Topic topic = session.createTopic("test-topic");
        //使用session对象创建一个consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(m -> {
            //取消息的内容
            if (m instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) m;
                try {
                    String text = textMessage.getText();
                    //打印消息
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //系统等待接收消息
        /*while (true){
            Thread.sleep(1000);
        }*/
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }



    @Test
    public void testPageHelper() throws Exception{

        PageHelper.startPage(1, 10);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        ItemMapper itemMapper = applicationContext.getBean(ItemMapper.class);



    }


}
