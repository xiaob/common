package com.yuan.common.brpc.test;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianClient {

	public static void main(String[] args)throws Exception {
		String url = "http://localhost:8080/hello/hello";
		HessianProxyFactory factory = new HessianProxyFactory();
		Hello hello = (Hello)factory.create(Hello.class, url);
		
		long totalTime = 0L;
		for(int i=0; i<200; i++){
			long start = System.currentTimeMillis();
			hello.hello("hello message");
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start) + "毫秒");
			totalTime += (end - start);
		}
		System.out.println("平均耗时: " + totalTime/20 + "毫秒");
		
		System.out.println("hello(): " + hello.hello("hello message"));

	}

}
