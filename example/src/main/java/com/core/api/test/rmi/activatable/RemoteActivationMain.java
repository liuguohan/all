package com.core.api.test.rmi.activatable;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.MarshalledObject;
import java.rmi.RMISecurityManager;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import com.core.api.test.rmi.domain.UserInfo;

/**
 * https://blog.csdn.net/yinwenjie/article/details/49338263
 * @author Administrator
 *
 */
public class RemoteActivationMain {

	
	public static void main(String[] args) throws ActivationException, IOException, AlreadyBoundException {
		System.setSecurityManager(new RMISecurityManager());
		System.setProperty("java.rmi.activation.port", "1099");
		
		
		// 设置各种参数
		Properties props = new Properties();
		
		
		// 最重要的参数是给rmi 激活服务相应的权限
        // all.policy文件的配置在后文中说明
		props.put("java.security.policy", "F:/项目/面包汽车/API/workspace/example/src/all.policy");
		
		
		
		/* 
         * 这里对ActivationDesc构造函数中的参数进行一下说明：
         * groupID：就是在registerGroup时系统生成的ActivationGroupID对象
         * className：这里就是testRMI.RemoteActivationServiceImpl具体的RemoteServiceInterface接口
         * location：相当于classpath。这样注册程序才知道如何序列化类.
         * 实际上之前我们建立RemoteActivationServiceImpl的构造函数时，实际上已经location写死了
         * 这里只是为了讲解desc和RemoteActivationServiceImpl中构造函数的关系，又进行了一次参数指定
         * 
         * MarshalledObject：一种序列化和反序列化方式，参见：
         * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/rmi/MarshalledObject.html
         * */
		ActivationGroupDesc groupdesc = new ActivationGroupDesc(props, null);
		ActivationGroupID grouid = ActivationGroup.getSystem().registerGroup(groupdesc);
		UserInfo userinfo = new UserInfo();
		
		ActivationDesc desc = new ActivationDesc(grouid, "com.core.api.test.rmi.activatable.RemoteActivationServiceImpl", 
				"file://F:\\项目\\面包汽车\\API\\workspace\\example\\target\\classes", new MarshalledObject<UserInfo>(userinfo));
		
		
		
		/*
         * 是不是与UnicastRemoteObject的注册方式不一样啊。
         * 之所以比较复杂，是因为这个Remote Object将会序列化到RMI Registry所在的JVM进行运行。
         * 并适时被“激活”运行
         * */
		RemoteServiceInterface server = (RemoteServiceInterface)Activatable.register(desc);
		
		
		/*
         * 和执行UnicastRemoteObject注册的情况不同，这里绑定Remote Object成功后，这个JVM将会退出
         * 因为之后的RMI调用将和它没有一点关系了。
         * */
        //Naming.bind("rmi://192.168.61.1:1099/queryAllUserinfo",server);
		Registry registry = LocateRegistry.getRegistry("192.168.10.156", 1099);
		registry.bind("queryAllUserinfo", server);
		
	}
}
