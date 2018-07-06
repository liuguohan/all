package com.core.api.test.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMQSender {

	public static void main(String[] args) throws JMSException {
		final ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"activemq.xml"});
		
		ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory)context.getBean("connectionFactory");
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination queue = (Destination)context.getBean("destination");
		MessageProducer producer = session.createProducer(queue);
		
		TextMessage message = session.createTextMessage("hello,....");
		try {
			producer.send(message);
			session.commit();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		}
		
		
		producer.close();
		connection.close();
		
	}
	
}
