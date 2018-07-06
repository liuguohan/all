package com.core.api.test.dubbo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.core.api.test.dubbo.service.MyService;

public class ClientMainProcessor {

	static{
		BasicConfigurator.configure();
	}
	
	private static Object wObject = new Object();
	
	private static final Log LOGGER = LogFactory.getLog(ClientMainProcessor.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext app = new ClassPathXmlApplicationContext(new String[]{"dubbo-consumer.xml"});
		
		// 开始RPC调用
		MyService myService = (MyService)app.getBean("myService");
		
		LOGGER.info("myService = " + myService.doMyTest("1234", "abcde"));
		
		// 这里锁定这个应用程序，和DUBBO框架本身的工作原理没有任何关系，只是为了让其不退出
		synchronized (ClientMainProcessor.wObject) {
			ClientMainProcessor.wObject.wait();
		}
		
	}
	
}
