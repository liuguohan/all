package com.core.api.test.io.noblockingio2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketServerThread4 implements Runnable {

	private Socket socket;
	
	public SocketServerThread4(Socket socket) {
		super();
		this.socket = socket;
	}



	@Override
	public void run() {
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer sourcePort = socket.getPort();
            int maxLen = 2048;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            StringBuffer message = new StringBuffer();
            //下面我们收取信息（这里还是阻塞式的,一直等待，直到有数据可以接受）
            //因为你这个式socket编程，输入流肯定是从客户端得到的，
			//此时虽然已将客户端传过来的数据读取完毕，但服务端仍旧在运行，监听客户端的状态，并等待客户端再次发开数据，
			//所以客户端此时的输入流肯定没有关闭，程序此时处于等待状态，while循环并没有跳出！所以后边的输出语句就不会执行到！
            socket.setSoTimeout(10);
            BIORead:while(true){
            	try {
                    while((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
                        message.append(new String(contextBytes , 0 , realLen));
                        /*
                         * 我们假设读取到“over”关键字，
                         * 表示客户端的所有信息在经过若干次传送后，完成
                         * */
                        if(message.indexOf("over") != -1) {
                            break BIORead;
                        }
                    }
                } catch(SocketTimeoutException e2) {
                    //===========================================================
                    //      执行到这里，说明本次read没有接收到任何数据流
                    //      主线程在这里又可以做一些事情，记为Y
                    //===========================================================
                	System.out.println("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件Y的处理时间");
                    //continue;
                    break BIORead;
                }
            }
            	
            
            //下面打印信息
            System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);

            //下面开始发送信息
            out.write("回发响应信息！".getBytes());

            //关闭
            out.close();
            in.close();
            socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//试图关闭
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
                if(this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
		}
		
		
	}

}
