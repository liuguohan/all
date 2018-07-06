package com.core.api.test.rmi.activatable;

import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationID;
import java.util.ArrayList;
import java.util.List;

import com.core.api.test.rmi.domain.UserInfo;

public class RemoteActivationServiceImpl extends Activatable implements RemoteServiceInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6960138728679761368L;

	/**
     * 这个构造函数式必须的，不然客户端会报：<br>
     * java.lang.NoSuchMethodException: testRMI.RemoteActivationServiceImpl.<init>(java.rmi.activation.ActivationID, java.rmi.MarshalledObject) 异常<br>
     * 在这个方法里面，您可以调用任何一个Activatable父级的构造函数。这里我们调用的是最简单的一个
     * @throws RemoteException
     * @throws ActivationException 
     */
	protected RemoteActivationServiceImpl(ActivationID id, MarshalledObject<?> data) throws RemoteException, ActivationException {
		super("file://F:\\项目\\面包汽车\\API\\workspace\\example\\target\\classes", data, false , 0);
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
