package com.core.api.test.rmi.cluster;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class SingleRegistry {

	private final static Object wObject = new Object();
	
	public static void main(String[] args) throws RemoteException, InterruptedException {
		LocateRegistry.createRegistry(1099);
		
		synchronized (wObject) {
			wObject.wait();
		}
	}
	
}
