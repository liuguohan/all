package com.core.api.test.io.noblockingio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketServer4 {

	private static Object xWait = new Object();
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = null;
		
		try {
			
			serverSocket = new ServerSocket(83);
			serverSocket.setSoTimeout(100);
			while(true){
				Socket socket = null;
				try {
					socket = serverSocket.accept();
				} catch (SocketTimeoutException e1) {
					//===========================================================
                    //      执行到这里，说明本次accept没有接收到任何数据报文
                    //      主线程在这里就可以做一些事情，记为X
                    //===========================================================
                    synchronized (SocketServer4.xWait) {
                        System.out.println("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件X的处理时间");
                        SocketServer4.xWait.wait(10);
                    }
                    continue;
				}
				
				//当然业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                //最终改变不了.accept()只能一个一个接受socket连接的情况
				SocketServerThread4 socketServerThread4 = new SocketServerThread4(socket);
				new Thread(socketServerThread4).start();
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
        } finally {
            if(serverSocket != null) {
                serverSocket.close();
            }
        }
	}
	
}
