package com.core.api.test.dubbo.service.impl;

import org.springframework.stereotype.Component;

import com.core.api.test.dubbo.service.MyService;


@Component("MyServiceImpl")
public class MyServiceImpl implements MyService {

	@Override
	public String doMyTest(String field1, String field2) {
		
		return field1 + field2;
	}

}
