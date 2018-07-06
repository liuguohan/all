package com.core.api.test.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

public class ThreadLock {

	static{
		BasicConfigurator.configure();
	}
	
	private final static Object WAIT_OBJECT = new Object();
	
	private final static Log LOGGER = LogFactory.getLog(ThreadLock.class);
	
	public static void main(String[] args) {
		
		Thread threadA = new Thread(new Runnable() {
			public void run() {
				synchronized (ThreadLock.WAIT_OBJECT) {
					ThreadLock.LOGGER.info("FIRST TODO");
				}
			}
		});
		
		Thread threadB = new Thread(new Runnable() {
			public void run() {
				synchronized (ThreadLock.WAIT_OBJECT) {
					ThreadLock.LOGGER.info("SECOND TODO");
				}
			}
		});
		
		threadA.start();
		threadB.start();
	}
	
	
}
