package com.core.api.test.staticC;

import java.util.Random;

public class RealSubject implements Subject {

	@Override
	public void operation() throws Exception {

		this.exec();
	}

	public void exec() throws Exception{
		System.out.println("执行中。。。。。");
		float value = new Random().nextFloat();
		if( value < 0.01){
			throw new Exception("抛异常");
		}
	}
	
}
