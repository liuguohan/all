package com.core.api.test.lockandthread;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		new SemaphoreTest().doTest();
	}
	
	public void doTest() {
		
		Semaphore semp = new Semaphore(5, false);
		// 我们创建10个线程，并通过0-9的index进行编号
		for(int index = 0; index < 10 ; index ++) {
			
			Thread semaphoreThread = new Thread(new semaphoreRunnableNonFair(semp, index));
					semaphoreThread.start();
		}
		
	}
	
	
	private static class semaphoreRunnableNonFair implements Runnable {
		
		private Semaphore semp;
		
		private Integer index;

		public semaphoreRunnableNonFair(Semaphore semp, Integer index) {
			super();
			this.semp = semp;
			this.index = index;
		}

		@Override
		public void run() {
			try {
				System.out.println("线程" + this.index + "等待信号。。。。。。");
				this.semp.acquire();
				synchronized (this) {
					System.out.println("index 为 " + this.index + " 的线程，获得信号，开始处理业务");
					this.wait(5000);
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} finally {
				// 最后都要释放这个信号/证书
				this.semp.release();
			}
			
		}
		
	}
	
	
}
