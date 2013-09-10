package com.yuan.common.om.binary;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serialize.ArraySerializer;
import com.esotericsoftware.kryo.serialize.CollectionSerializer;
import com.esotericsoftware.kryo.serialize.EnumSerializer;
import com.esotericsoftware.kryo.serialize.MapSerializer;
import com.yuan.common.util.ReflectUtil;

public class BinaryTest {

	public static void main(String[] args) {
		Kryo k = new Kryo();
		k.register(Student.class);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		k.writeObject(buf, new Student("wang", 20));
		
		byte[] serialValue = readBuf(buf);
		
		Student student = k.readObject(ByteBuffer.wrap(serialValue), Student.class);
		System.out.println(student.getName());
		System.out.println(student.getAge());
		
		//数组
		buf.clear();
		k.register(String[].class, new ArraySerializer(k));
		String[] arr1 = {"111", "222"};
		k.writeObject(buf, arr1);
		serialValue = readBuf(buf);
		String[] arr2 = k.readObject(ByteBuffer.wrap(serialValue), String[].class);
		System.out.println(arr2[0]);
		
		//集合
		buf.clear();
		k.register(ArrayList.class, new CollectionSerializer(k));
		List<Student> list = new ArrayList<Student>();
		list.add(new Student("wang", 20));
		list.add(new Student("wang", 20));
		k.writeObject(buf, list);
		serialValue = readBuf(buf);
//		List<Student> list2 = ReflectUtil.castList(k.readObject(ByteBuffer.wrap(serialValue), ArrayList.class), Student.class);
		System.out.println(list.get(0).getName());
		
		//MAP
		buf.clear();
		k.register(HashMap.class, new MapSerializer(k));
		Map<Integer, String> map1 = new HashMap<Integer, String>();
		map1.put(1, "11");
		map1.put(2, "22");
		k.writeObject(buf, map1);
		serialValue = readBuf(buf);
		Map<Integer, String> map2 = ReflectUtil.castMap(k.readObject(ByteBuffer.wrap(serialValue), HashMap.class), Integer.class, String.class);
		System.out.println(map2.get(1));
		
		//枚举
		buf.clear();
		k.register(Color.class, new EnumSerializer(Color.class));
		Color color = Color.RED;
		k.writeObject(buf, color);
		serialValue = readBuf(buf);
		Color color2 = k.readObject(ByteBuffer.wrap(serialValue), Color.class);
		System.out.println(color2);
		
		buf.clear();
		k.register(Grade.class);
		k.writeObject(buf, new Grade());
		
		serialValue = readBuf(buf);
		
		Grade g = k.readObject(ByteBuffer.wrap(serialValue), Grade.class);
		System.out.println("g.name = " + g.name);
		System.out.println(g.f1);
	}
	
	public static byte[] readBuf(ByteBuffer buf){
		int size = buf.position();
		byte[] newBuf = new byte[size];
		for(int i=0; i<size; i++){
			newBuf[i] = buf.get(i);
		}
		return newBuf;
	}

}

