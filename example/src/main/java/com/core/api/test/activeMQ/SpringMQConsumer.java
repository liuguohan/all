package com.core.api.test.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMQConsumer {

	private static Session session = null;
	
	public static void main(String[] args) throws JMSException, InterruptedException {
		
		final ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"activemq.xml"});
		
		// 定义JMS-ActiveMQ连接信息(默认使用的消息协议为openwire，openwire的默认端口号为61616)
		ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory)context.getBean("connectionFactory");
    	
    	//进行连接
    	Connection connection = connectionFactory.createConnection();
    	connection.start();
    	
    	//建立会话(设置为自动ack)
    	session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
    	
    	//建立Queue（当然如果有了就不会重复建立）
    	Destination sendQueue = (Destination)context.getBean("destination");
    	
    	//建立消息发送者对象
    	MessageConsumer consumer = session.createConsumer(sendQueue);
    	
    	consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message arg0) {
				// 接收到消息后，不需要再发送ack了。
				TextMessage message = (TextMessage)arg0;
				
				try {
					System.out.println("Message = " + message.getText());
					session.commit();
				} catch (JMSException e) {
					try {
						session.rollback();
					} catch (JMSException e1) {
						e1.printStackTrace();
					}
				}
				
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
