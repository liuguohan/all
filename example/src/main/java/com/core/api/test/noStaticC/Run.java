package com.core.api.test.noStaticC;

import java.lang.reflect.Proxy;

public class Run {

	public static void main(String[] args) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ProxyInvocationHandle handle = new ProxyInvocationHandle();
		Object proxy = Proxy.newProxyInstance(classLoader, new Class<?>[]{TargetOneInterface.class, TargetTwoInterface.class}, handle);
		
		TargetOneInterface one = (TargetOneInterface)proxy;
		one.doSomething();
		one.handleSomething();
		
		TargetTwoInterface two = (TargetTwoInterface)proxy;
		two.findSomething();
		two.findSomethingByField(111);
	}
	
}
