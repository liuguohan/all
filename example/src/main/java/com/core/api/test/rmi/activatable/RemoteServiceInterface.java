package com.core.api.test.rmi.activatable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.core.api.test.rmi.domain.UserInfo;

public interface RemoteServiceInterface extends Remote {

	public List<UserInfo> queryAllUserinfo() throws RemoteException;
	
}
