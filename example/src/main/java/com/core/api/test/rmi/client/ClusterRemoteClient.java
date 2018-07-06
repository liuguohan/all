package com.core.api.test.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.core.api.test.rmi.cluster.RemoteServiceInterface;
import com.core.api.test.rmi.domain.UserInfo;

public class ClusterRemoteClient {

	private static Object wObject = new Object();
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
		 
		RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface)Naming.lookup("rmi://192.168.10.156/queryAllUserinfo");
		

		for(;;) {
			List<UserInfo> users = remoteServiceInterface.queryAllUserinfo();
			System.out.println("users.size() = " +users.size());
			synchronized (wObject) {
				ClusterRemoteClient.wObject.wait(1000);
			}
		}
		
		
	}
	
}
