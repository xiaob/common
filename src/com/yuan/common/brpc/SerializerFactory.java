package com.yuan.common.brpc;

public class SerializerFactory {

	public static InvocationSerializer newInvocationSerializer(){
		return new JdkInvocationSerializer();
	}
}
