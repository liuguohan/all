package com.core.api.test.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.core.api.test.rmi.domain.UserInfo;
import com.core.api.test.rmi.remote.RemoteServiceInterface;

/**
 * 客户端调用RMI测试
 * @author yinwenjie
 *
 */
public class RemoteClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface)Naming.lookup("rmi://192.168.10.156/queryAllUserinfo");
		List<UserInfo> users = remoteServiceInterface.queryAllUserInfo();
		System.out.println("users.size() = " + users.size() + " , users'content = " + users.toString());
	}
	
}
