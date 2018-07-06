package com.core.api.test.rmi.cluster;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.core.api.test.rmi.domain.UserInfo;

public class RemoteUnicastServiceImpl extends UnicastRemoteObject implements RemoteServiceInterface{

	private static final long serialVersionUID = 7352393395647401333L;

	protected RemoteUnicastServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public List<UserInfo> queryAllUserinfo() throws RemoteException {
		List<UserInfo> users = new ArrayList<UserInfo>();

        UserInfo user1 = new UserInfo();
        user1.setUserAge(21);
        user1.setUserDesc("userDesc1");
        user1.setUserName("userName1");
        user1.setUserSex(true);
        users.add(user1);

        UserInfo user2 = new UserInfo();
        user2.setUserAge(21);
        user2.setUserDesc("userDesc2");
        user2.setUserName("userName2");
        user2.setUserSex(false);
        users.add(user2);
        return users;
	}

}
