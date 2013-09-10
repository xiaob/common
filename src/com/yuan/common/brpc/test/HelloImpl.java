package com.yuan.common.brpc.test;

import java.io.Serializable;

public class HelloImpl implements Hello,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String hello(String message) {
		System.out.println("hello");
		for(int i=0;i<5;i++){
			System.out.println("111111111");
		}
		return message;
	}

}
