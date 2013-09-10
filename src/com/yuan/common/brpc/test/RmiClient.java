package com.yuan.common.brpc.test;

import java.rmi.Naming;

public class RmiClient {

	public static void main(String[] args)throws Exception {
		Hello hello = (Hello)Naming.lookup("rmi://localhost:8888/RHello");
		System.out.println(hello);
		for(int i=0; i<10; i++){
			long start = System.currentTimeMillis();
			System.out.println(hello.hello("hello message"));
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start) + "毫秒");
		}
	}

}
