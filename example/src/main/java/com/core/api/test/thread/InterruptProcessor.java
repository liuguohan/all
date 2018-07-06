package com.core.api.test.thread;

public class InterruptProcessor {

	public static void main(String[] args) {
		
		//thread one 线程 
		Thread threadOne = new Thread( new Runnable() {
			public void run() {
				Thread cuThread = Thread.currentThread();
				// 并不是线程收到interrupt信号，就会立刻终止； 
                // 线程需要检查自生状态是否正常，然后决定下一步怎么走。
				while(!cuThread.isInterrupted()) {
					/*
                     * 这里打印一句话，说明循环一直在运行
                     * 但是正式系统中不建议这样写代码，因为没有中断（wait、sleep）的无限循环是非常耗费CPU资源的
                     * */
					System.out.println("Thread One 一直在运行！");
				}
				System.out.println("thread one 正常结束！" + cuThread.isInterrupted());
			}
		});
		
		//thread two线程
		Thread threadTwo = new Thread(new Runnable() {
			public void run() {
				Thread cuThread = Thread.currentThread();
				while(!cuThread.isInterrupted()){
					synchronized (cuThread) {
						try {
							// 通过wait进入阻塞
							cuThread.wait();
						} catch (InterruptedException e) {
							e.printStackTrace(System.out);
                            System.out.println("thread two 由于中断信号，异常结束！" + cuThread.isInterrupted());
                            return;
						}
					}
				}
				
				System.out.println("thread two 正常结束！");
			}
		});
		
		threadOne.start();
        threadTwo.start();
        // 您可以通过eclipse工具在这里打上端点，以保证threadOne和threadTwo完成了启动
        // 当然您还可以使用其他方式来确保这个事情
        System.out.println("两个线程正常运行，现在开始发出中断信号");
        threadOne.interrupt();
        threadTwo.interrupt();
	}
	
}
