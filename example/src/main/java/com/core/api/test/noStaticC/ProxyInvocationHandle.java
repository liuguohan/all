package com.core.api.test.noStaticC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProxyInvocationHandle implements InvocationHandler {

	private static Map<Class<?>, Object> simulatorContainer = new HashMap<>();

	  static {
	    // 这是TargetOneInterface的实现
	    simulatorContainer.put(TargetOneInterface.class, new TargetOneImpl());
	  }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		System.out.println("====================================================");
	    System.out.println("代理者的对象：" + proxy.getClass().getName());
	    // 被代理的接口
	    Class<?> targetClass = method.getDeclaringClass();
	    System.out.println("被代理的接口类：" + targetClass.getName());
	    System.out.println("被代理的方法：" + method.getName());
	    
	    Object target = simulatorContainer.get(targetClass);
	    if(target == null) {
	        return null;
	      }
	    method.invoke(target, args);
	    
	    if(args == null) {
	      return null;
	    }

	    System.out.println("被代理的调用过程参数类型：");
	   
	    
	    
	    // 在这里可以返回调用结果 
	    return null;
	}

}
