package com.core.api.test.dubbo;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceMainProcessor {

	//Log4j的日志基本配置（要使用这种方式首先引入log4j的支持）
	static{
		BasicConfigurator.configure();
	}
	
	private static Object wObject = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		
		new ClassPathXmlApplicationContext(new String[]{"dubbo-provider.xml"});
		
		
		synchronized (ServiceMainProcessor.wObject) {
			ServiceMainProcessor.wObject.wait();
		}
	}
	
}
