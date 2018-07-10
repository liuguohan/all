package com.core.api.test.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

public class ThreadPoolTestMain {

	@Test
	public void synTest() throws InterruptedException{
		
		/*
		 * 这是一个内部没有任何容量的阻塞队列，任何一次插入操作的元素都要等待相对的删除/读取操作，
		 * 否则进行插入操作的线程就要一直等待，反之亦然。
		 * 
		 */
		SynchronousQueue<Object> queue = new SynchronousQueue<Object>();
		// 不要使用add，因为这个队列内部没有任何容量，所以会抛出异常“IllegalStateException”
		//queue.add(new Object());
		queue.put(new Object());
		
	}
	
	@Test
	public void arrayTest() throws InterruptedException{
		
		/*
		 * 一个由数组支持的有界阻塞队列。
		 * 此队列按 FIFO（先进先出）原则对元素进行排序。
		 * 新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。
		 * 这是一个典型的“有界缓存区”，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。
		 * 一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；
		 * 试图从空队列中提取元素将导致类似阻塞。
		 * 
		 */
		
		ArrayBlockingQueue<Object> arrayQueue = new ArrayBlockingQueue<Object>(2);
		// 插入第一个对象
		arrayQueue.put(new Object());
		// 插入第二个对象
		arrayQueue.put(new Object());
		// 插入第三个对象时，这个操作线程就会被阻塞。
		arrayQueue.put(new Object());
		// 请不要使用add操作，和SynchronousQueue的add操作一样，它们都使用了AbstractQueue中的add实现
		
	}
	
	@Test
	public void linkedBlockingQueueTest() throws InterruptedException{
		
		/*
		 * LinkedBlockingQueue是我们在ThreadPoolExecutor线程池中常应用的等待队列。
		 * 它可以指定容量也可以不指定容量。
		 * 由于它具有“无限容量”的特性，所以我还是将它归入了无限队列的范畴（实际上任何无限容量的队列/栈都是有容量的，这个容量就是Integer.MAX_VALUE）。
		 * 
		 * LinkedBlockingQueue的实现是基于链表结构，而不是类似ArrayBlockingQueue那样的数组。
		 * 但实际使用过程中，您不需要关心它的内部实现，如果您指定了LinkedBlockingQueue的容量大小，那么它反映出来的使用特性就和ArrayBlockingQueue类似了。
		 * 
		 */
		
		LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(2);
		// 插入第一个对象
		queue.put(new Object());
		// 插入第二个对象
		queue.put(new Object());
		// 插入第三个对象时，这个操作线程就会被阻塞。
		queue.put(new Object());
		
	}
		
	@Test
	public void linkedBlockingQueueTest1() throws InterruptedException{
		
		LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
		// 插入第一个对象
		queue.put(new Object());
		// 插入第二个对象
		queue.put(new Object());
		// 插入第N个对象时，都不会阻塞
		queue.put(new Object());
		
	}
	
	@Test
	public void linkedBlockingDequeTest() throws InterruptedException{
		
		/*
		 * LinkedBlockingDeque是一个基于链表的双端队列。
		 * LinkedBlockingQueue的内部结构决定了它只能从队列尾部插入，从队列头部取出元素；
		 * 但是LinkedBlockingDeque既可以从尾部插入/取出元素，还可以从头部插入元素/取出元素。
		 * 
		 */
		
		LinkedBlockingDeque<TempObject> queue = new LinkedBlockingDeque<TempObject>();
		// push ，可以从队列的头部插入元素
		queue.push(new TempObject(1));
		queue.push(new TempObject(2));
		queue.push(new TempObject(3));
		// poll ， 可以从队列的头部取出元素
		TempObject tempObject = queue.poll();
		// 这里会打印 tempObject.index = 3
		System.out.println("tempObject.index = " + tempObject.getIndex());
		
		// put ， 可以从队列的尾部插入元素
		queue.put(new TempObject(4));
		queue.put(new TempObject(5));
		// pollLast , 可以从队列尾部取出元素
		tempObject = queue.pollLast();
		// 这里会打印 tempObject.index = 5
		System.out.println("tempObject.index = " + tempObject.getIndex());
	}
	
	@Test
	public void priorityBlockingQueueTest() throws InterruptedException{
		
		/*
		 * PriorityBlockingQueue是一个按照优先级进行内部元素排序的无限队列。
		 * 存放在PriorityBlockingQueue中的元素必须实现Comparable接口，这样才能通过实现compareTo()方法进行排序。
		 * 优先级最高的元素将始终排在队列的头部；
		 * PriorityBlockingQueue不会保证优先级一样的元素的排序，也不保证当前队列中除了优先级最高的元素以外的元素，随时处于正确排序的位置。
		 * 
		 */
		
		PriorityBlockingQueue<TempObject> queue = new PriorityBlockingQueue<TempObject>();
		queue.put(new TempObject(-5));
		queue.put(new TempObject(5));
		queue.put(new TempObject(-1));
		queue.put(new TempObject(1));
		
		// 第一个元素是5
		// 实际上在还没有执行priorityQueue.poll()语句的时候，队列中的第二个元素不一定是1
		TempObject tempObject = queue.poll();
		System.out.println("tempObject.index = " + tempObject.getIndex());
		// 第二个元素是1
		tempObject = queue.poll();
		System.out.println("tempObject.index = " + tempObject.getIndex());
		// 第三个元素是-1
		tempObject = queue.poll();
		System.out.println("tempObject.index = " + tempObject.getIndex());
		// 第四个元素是-5
		tempObject = queue.poll();
		System.out.println("tempObject.index = " + tempObject.getIndex());
		
	}
	
	@Test
	public void linkedTransferQueueTest() throws InterruptedException{
		
		/*
		 * LinkedTransferQueue也是一个无限队列，它除了具有一般队列的操作特性外（先进先出），
		 * 还具有一个阻塞特性：LinkedTransferQueue可以由一对生产者/消费者线程进行操作，
		 * 当消费者将一个新的元素插入队列后，消费者线程将会一直等待，直到某一个消费者线程将这个元素取走，反之亦然。
		 * 
		 */
		
		LinkedTransferQueue<TempObject> queue = new LinkedTransferQueue<TempObject>();
		// 这是一个生产者线程
		Thread producerThread = new Thread(new ProducerRunnable(queue));
		// 这里有两个消费者线程
		Thread consumerRunnable1 = new Thread(new ConsumerRunnable(queue));
		Thread consumerRunnable2 = new Thread(new ConsumerRunnable(queue));

		// 开始运行
		producerThread.start();
		consumerRunnable1.start();
		consumerRunnable2.start();

		// 这里只是为了main不退出，没有任何演示含义
		Thread currentThread = Thread.currentThread();
		synchronized (currentThread) {
		    currentThread.wait();
		}
	}

	
	// 这个元素类，必须实现Comparable接口
	private static class TempObject implements Comparable<TempObject> {

		private int index;
		
		
		public TempObject(int index) {
			super();
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public int compareTo(TempObject o) {
			return o.index  - this.index;
		}
		
	}
	
	/**
	 * 生产者线程
	 * @author yinwenjie
	 */
	private static class ProducerRunnable implements Runnable {

		private LinkedTransferQueue<TempObject> queue;
		
		public ProducerRunnable(LinkedTransferQueue<TempObject> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			for(int index = 1;; index++){
				try {
					// 向LinkedTransferQueue队列插入一个新的元素
	                // 然后生产者线程就会等待，直到有一个消费者将这个元素从队列中取走
					this.queue.transfer(new TempObject(index));
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
			
		}
		
	}
	
	/**
	 * 消费者线程
	 * @author yinwenjie
	 */
	private static class ConsumerRunnable implements Runnable {

		private LinkedTransferQueue<TempObject> queue;
		
		public ConsumerRunnable(LinkedTransferQueue<TempObject> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			
			Thread cuThread = Thread.currentThread();
			
			while( !cuThread.isInterrupted() ) {
				try {
					// 等待，直到从LinkedTransferQueue队列中得到一个元素
					TempObject tempObject = this.queue.take();
					System.out.println("线程（" + cuThread.getId() + "）取得tempObject.index = " + tempObject.getIndex());
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
			
		}
		
	}
}
