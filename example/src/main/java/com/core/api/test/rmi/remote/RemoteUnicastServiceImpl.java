package com.core.api.test.rmi.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.core.api.test.rmi.domain.UserInfo;

/**
 * RMI 服务接口RemoteServiceInterface的具体实现<br>
 * 请注意这里继承的是UnicastRemoteObject父类。
 * 继承于这个父类，表示这个Remote Object是“存在于本地”的RMI服务实现
 * （这句话后文会解释）
 * @author yinwenjie
 *
 */
public class RemoteUnicastServiceImpl extends UnicastRemoteObject implements RemoteServiceInterface {

	private static final long serialVersionUID = 7638205645105298619L;

	/**
     * 注意Remote Object没有默认构造函数
     * @throws RemoteException
     */
	protected RemoteUnicastServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public List<UserInfo> queryAllUserInfo() throws RemoteException {
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
