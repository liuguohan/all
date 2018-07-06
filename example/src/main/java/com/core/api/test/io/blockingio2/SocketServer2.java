package com.core.api.test.io.blockingio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer2 {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(83);
		
		try {
			while(true){
				Socket socket = serverSocket.accept();
				SocketServerThread2 socketServerThread2 = new SocketServerThread2(socket);
				new Thread(socketServerThread2).start();
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
