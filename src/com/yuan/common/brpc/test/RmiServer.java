package com.yuan.common.brpc.test;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

	public static void main(String args[])throws Exception{
		LocateRegistry.createRegistry(8888);
		HelloImpl helloImpl = new HelloImpl();
		System.out.println(helloImpl);
		Naming.rebind("rmi://localhost:8888/RHello", helloImpl);
		
		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}
	
}
