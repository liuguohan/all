package com.core.api.test.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;

public class AMQPProducer {

	public static void main(String[] args) throws Throwable {
		
		// 注意，JMS-AMQP使用的是Apache QPID的实现。如果您需要运行这段代码，请导入QPID的客户端
		/*
         * <dependency>
         *  <groupId>org.apache.qpid</groupId>
         *  <artifactId>qpid-amqp-1-0-client-jms</artifactId>
         *  <version>0.32</version>
         * </dependency>
         * */
		ConnectionFactoryImpl factory = ConnectionFactoryImpl.createFromURL("amqp://192.168.110.66:5672");
		
		Connection connection = factory.createQueueConnection();
		connection.start();
		
		// 建立会话，连接到叫做/test的Queue上
		Session session =  connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination sendQueue = session.createQueue("/test");
		MessageProducer producer = session.createProducer(sendQueue);
		
		// 开始发送消息
		TextMessage message = session.createTextMessage();
		message.setText("2325623257");
		producer.send(message);
		
		producer.close();
		connection.close();
		
	}
	
}
