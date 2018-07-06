package com.core.api.test.io;

public class test implements Runnable{

	public static void main(String[] args) {
		new Thread(new test()).start();
	}

	@Override
	public void run() {
		while(true){
			System.out.println("ad");
			throw new RuntimeException();
		}
		
	}
	
}
