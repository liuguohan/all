package com.core.api.test.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

public class SyncThread implements Runnable{

	private final static Log LOGGER = LogFactory.getLog(SyncThread.class);
	
	private Integer value;
	
	private static Integer NOWVALUE;
	
	static {
		BasicConfigurator.configure();
	}
	
	public SyncThread(int value) {
		this.value = value;
	}
	
	public /*synchronized*/ void doOtherThing(){
		
		synchronized(SyncThread.class) {
			NOWVALUE = this.value;
			LOGGER.info("当前NOWVALUE的值：" + NOWVALUE);
		}
		
	}
	
	@Override
	public void run() {
//		Thread currentThread = Thread.currentThread();
//		Long id = currentThread.getId();
		this.doOtherThing();
	}

	public static void main(String[] args) {
		Thread syncThread1 = new Thread(new SyncThread(10));
        Thread syncThread2 = new Thread(new SyncThread(100));

        syncThread1.start();
        syncThread2.start();
	}
	
}
