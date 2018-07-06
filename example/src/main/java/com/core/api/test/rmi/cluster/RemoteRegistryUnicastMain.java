package com.core.api.test.rmi.cluster;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteRegistryUnicastMain {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		Registry registry = LocateRegistry.getRegistry("192.168.10.156", 1099);
		
		RemoteUnicastServiceImpl remoteUnicastServiceImpl = new RemoteUnicastServiceImpl();
		
		registry.bind("queryAllUserinfo", remoteUnicastServiceImpl);
		
	}
	
}
