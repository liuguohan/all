package com.core.api.test.thread;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.BasicConfigurator;

public class ParentNotifyThread implements Runnable {

	/**
     * 这个对象的“钥匙”，为每个ChildNotifyThread对象所持有，
     * 模拟这个对象为所有ChildNotifyThread对象都要进行独占的现象
     */
	public final static Object WAIT_CHILDOBJECT = new Object();
	
	static {
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		new Thread(new ParentNotifyThread()).start();
	}

	@Override
	public void run() {
		
		CountDownLatch countDownLatch = new CountDownLatch(3);
		
		/*
         * 3个进行WAIT_CHILEOBJECT对象独立抢占的线程，观察情况
         * */
		int maxIndex = 3;
		for(int index = 0; index < maxIndex ; index++) {
			ChildNotifyThread childNotifyThread = new ChildNotifyThread(countDownLatch);
			new Thread(childNotifyThread).start();
		}
		
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/*
         * 请在这里加eclipse断点，
         * 以便保证ChildNotifyThread中的wait()方法首先被执行了。
         * 
         * 真实环境下，您可以通过一个布尔型（或者其他方式）进行阻塞判断
         * 还可以使用CountDownLatch类
         * */
		synchronized (ParentNotifyThread.WAIT_CHILDOBJECT) {
			ParentNotifyThread.WAIT_CHILDOBJECT.notify();//.notifyAll();
		}
		
	}
	
	
}
