package com.core.api.test.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 测试使用JMS API连接ActiveMQ
 * @author yinwenjie
 */
public class JMSConsumer {
    /**
     * 由于是测试代码，这里忽略了异常处理。
     * 正是代码可不能这样做
     * @param args
     * @throws RuntimeException
     */
    public static void main (String[] args) throws Exception {
        // 定义JMS-ActiveMQ连接信息(默认使用的消息协议为openwire，openwire的默认端口号为61616)
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.110.66:61616");
    	
    	//进行连接
    	Connection connection = connectionFactory.createConnection();
    	connection.start();
    	
    	//建立会话(设置为自动ack)
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	//建立Queue（当然如果有了就不会重复建立）
    	Destination sendQueue = session.createQueue("/test");
    	
    	//建立消息发送者对象
    	MessageConsumer consumer = session.createConsumer(sendQueue);
    	
    	consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				// 接收到消息后，不需要再发送ack了。
				System.out.println("Message = " + arg0);
				
			}
		});
    	
    	synchronized (JMSConsumer.class) {
    		JMSConsumer.class.wait();
		}
    	
    	//关闭
    	consumer.close();
    	connection.close();
    	
    }
}
