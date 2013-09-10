package com.yuan.common.brpc;

public interface InvocationSerializer {

	public byte[] serial(Object obj)throws Exception;
	public <T> T unserial(byte[] data, Class<T> clazz)throws Exception;
		
}
