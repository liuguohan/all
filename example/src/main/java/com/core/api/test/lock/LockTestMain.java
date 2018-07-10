package com.core.api.test.lock;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

public class LockTestMain {

	static {
		BasicConfigurator.configure();
	}
	
	private static Log LOGGER = LogFactory.getLog(LockTestMain.class);
	
	static ReentrantLock objectLock = new ReentrantLock();

	public static void main(String[] args) {
		
		new Thread( new Runnable() {
			public void run() {
				objectLock.lock();
				LockTestMain.LOGGER.info("do something first ...");
				objectLock.unlock();
			}
		}).start();
		
		new Thread( new Runnable() {
			public void run() {
				objectLock.lock();
				LockTestMain.LOGGER.info("do something second ...");
				objectLock.unlock();
			}
		}).start();
		
	}
		
		
	
}
