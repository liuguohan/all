package com.core.api.test.io.blockingio1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer1 {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(83);
		
		try {
			while(true){
				Socket socket = serverSocket.accept();
				
				//下面我们收取信息
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				int sourcePort = socket.getPort();
				
				int maxLen = 1024;
				byte[] contextByte = new byte[maxLen];
				int readLen;
				String message = "";
//				//这里也会被阻塞，直到有数据准备好
//				readLen = (in.read(contextByte, 0, maxLen));
//				//读取信息
//				message += new String(contextByte, 0, readLen);
			
				while( (readLen = (in.read(contextByte, 0, maxLen))) != -1 ) {
	            	message += new String(contextByte, 0 , readLen);
	            }
				
				//下面打印信息
				System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
				
				//下面开始发送信息
				out.write("回发响应信息！".getBytes());
				
				//关闭
				in.close();
				out.close();
				socket.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(serverSocket != null) {
                serverSocket.close();
            }
		}
		
	}
	
}
