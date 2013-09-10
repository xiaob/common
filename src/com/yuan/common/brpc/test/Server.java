package com.yuan.common.brpc.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.yuan.common.brpc.ServiceEndpoint;

public class Server {

	public static void main(String[] args) {
		ServiceEndpoint.addService(Hello.class, new HelloImpl());
		try {
			ServiceEndpoint.publish(1000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
