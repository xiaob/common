package com.yuan.common.brpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkInvocationSerializer implements InvocationSerializer {

	@Override
	public byte[] serial(Object obj)throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(obj);
        out.close();
        
		return baos.toByteArray();
	}

	@Override
	public <T> T unserial(byte[] data, Class<T> clazz)throws Exception {
		Object obj = new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
		return clazz.cast(obj);
	}

}
