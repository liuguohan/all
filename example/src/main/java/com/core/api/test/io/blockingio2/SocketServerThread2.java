package com.core.api.test.io.blockingio2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketServerThread2 implements Runnable {

	private Socket socket;
	
	public SocketServerThread2(Socket socket) {
		super();
		this.socket = socket;
	}



	@Override
	public void run() {
		
		InputStream in = null;
		OutputStream out = null;
		try {
			//下面我们收取信息
			in = socket.getInputStream();
		    out = socket.getOutputStream();
			int sourcePort = socket.getPort();
			
			int maxLen = 1024;
			byte[] contextByte = new byte[maxLen];
			int readLen;
			String message = "";
			//使用线程，同样无法解决read方法的阻塞问题，
            //也就是说read方法处同样会被阻塞，直到操作系统有数据准备好
			//while( (readLen = (in.read(contextByte, 0, maxLen))) != -1 ) { 加where会报错
				readLen = (in.read(contextByte, 0, maxLen));
				//读取信息
				message += new String(contextByte, 0, readLen);
			//}
			
			//下面打印信息
			System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
			
			//下面开始发送信息
			out.write("回发响应信息！".getBytes());
			
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
