package com.core.api.test.thread;

public class SynchronizedUsage {

	//代码片段2 是对对象实例（多个）同步，其它都是对类（唯一）同步
	
	// 代码片段1
	static {
	    synchronized(SynchronizedUsage.class) {

	    }
	}

	// 代码片段2
	public synchronized void someMethod1() {

	}

	// 代码片段3
	public synchronized static void someMethod2() {

	}

	// 代码片段4
	public static void someMethod3() {
	    synchronized (SynchronizedUsage.class) {

	    }
	}

	// 代码片段5
	public void someMethod4() {
	    synchronized (SynchronizedUsage.class) {

	    }
	}
	
}
