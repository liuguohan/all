package com.core.api.test.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 测试使用JMS API连接ActiveMQ
 * @author yinwenjie
 */
public class JMSProducer {

	/**
     * 由于是测试代码，这里忽略了异常处理。
     * 正式代码可不能这样做
     * @param args
     * @throws RuntimeException
     */
	public static void main(String[] args) throws Exception {
		
		// 定义JMS-ActiveMQ连接信息
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.110.66:61616");
		Session session = null;
		Destination sendQueue;
		Connection connection = null;
		
		//进行连接
		connection = connectionFactory.createConnection();
		connection.start();
		
		//建立会话
		session = connection.createSession(true, Session.SESSION_TRANSACTED);
		
		//建立queue（当然如果有了就不会重复建立）
		sendQueue = session.createQueue("/test");
		
		//建立消息发送者对象
		MessageProducer sender = session.createProducer(sendQueue);
		
		TextMessage outMessage = session.createTextMessage();
		
		outMessage.setText("message content");
		
		//发送（JMS是支持事务的）
		sender.send(outMessage);
		
		session.commit();
		
		//关闭
		session.close();
		connection.close();
		
		
	}
	
}
