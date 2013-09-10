package com.yuan.common.brpc.test;

import java.rmi.RemoteException;

import com.caucho.hessian.server.HessianServlet;

public class HelloServlet extends HessianServlet implements Hello {

	private static final long serialVersionUID = 1L;

	@Override
	public String hello(String messgae) throws RemoteException {
		System.out.println("hello");
		for(int i=0;i<5;i++){
			System.out.println("111111111");
		}
		return messgae;
	}

}
