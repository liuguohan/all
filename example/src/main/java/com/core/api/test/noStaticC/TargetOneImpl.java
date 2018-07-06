package com.core.api.test.noStaticC;

public class TargetOneImpl implements TargetOneInterface{

	/*@Override
	public List<?> findSomething() {
		System.out.println("findSomething-----执行中");
		return null;
	}

	@Override
	public List<?> findSomethingByField(Integer field) {
		System.out.println("findSomethingByField-----执行中");
		return null;
	}*/

	@Override
	public void doSomething() {
		System.out.println("doSomething-----执行中");
		
	}

	@Override
	public void handleSomething() {
		System.out.println("handleSomething-----执行中");
		
	}

}
