package com.core.api.test.staticC;

public class Proxy implements Subject {

	private RealSubject subject;
	
	
	
	public Proxy() {
		this.subject = new RealSubject();
	}



	@Override
	public void operation() {
		try {
			System.out.println("执行前。。。。。");
		
			subject.operation();
			
			System.out.println("执行后。。。。。");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
