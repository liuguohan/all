package com.core.api.test.thread;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChildNotifyThread implements Runnable {

	private final static Log LOGGER = LogFactory.getLog(ChildNotifyThread.class);

	private CountDownLatch countDownLatch;
	
	public ChildNotifyThread() {
		super();
	}
	
	public ChildNotifyThread(CountDownLatch countDownLatch) {
		super();
		this.countDownLatch = countDownLatch;
	}

	
	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		long id = currentThread.getId();
		
		ChildNotifyThread.LOGGER.info("线程" + id + "启动成功，准备进入等待状态");
		
		synchronized (ParentNotifyThread.WAIT_CHILDOBJECT) {
			try {
				countDownLatch.countDown();
				ParentNotifyThread.WAIT_CHILDOBJECT.wait();
			} catch (Exception e) {
				ChildNotifyThread.LOGGER.error(e.getMessage(), e);
			}
		}
		
		//执行到这里，说明线程被唤醒了
		ChildNotifyThread.LOGGER.info("线程" + id + "被唤醒！");
	}
	
	
}
