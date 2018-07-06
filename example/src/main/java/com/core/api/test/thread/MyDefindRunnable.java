package com.core.api.test.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

public class MyDefindRunnable implements Runnable {

	static{
		BasicConfigurator.configure();
	}
	
	private final static Log LOGGER = LogFactory.getLog(MyDefindRunnable.class);

	@Override
	public void run() {
		long threadId = Thread.currentThread().getId();
		MyDefindRunnable.LOGGER.info("线程（" + threadId + "）做了一些事情，然后结束了。");
	}
	
	public static void main(String[] args) throws Exception {
        new Thread(new MyDefindRunnable()).start();
    }
	
}
