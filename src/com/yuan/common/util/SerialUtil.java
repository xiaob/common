package com.yuan.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化反序列化工具类
 * @author Windows
 *
 */
public class SerialUtil {

	public static byte[] serial(Serializable obj) throws IOException{
		if(obj == null){
			return null;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.close();
		
		return baos.toByteArray();
	}
	
	public static <T extends Serializable> T deserial(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		ois.close();
		
		if(obj == null){
			return null;
		}
		return clazz.cast(obj);
	}
}
