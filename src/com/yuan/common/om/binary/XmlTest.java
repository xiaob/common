package com.yuan.common.om.binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.yuan.common.util.ReflectUtil;

public class XmlTest {

	public static void main(String[] args) {
		XStream xstream = new XStream();

		String serialValue = xstream.toXML(new Student("wang", 20));
		System.out.println(serialValue);
		Student student = (Student)xstream.fromXML(serialValue);
		System.out.println(student.getName());
		
		//数组
		String[] arr1 = {"111", "222"};
		serialValue = xstream.toXML(arr1);
		System.out.println(serialValue);
		String[] arr2 = (String[])xstream.fromXML(serialValue);
		System.out.println(arr2[0]);
		
		//集合
		List<Student> list1 = new ArrayList<Student>();
		list1.add(new Student("wang", 20));
		list1.add(new Student("zhang", 21));
		serialValue = xstream.toXML(list1);
		System.out.println(serialValue);
		List<Student> list2 = toList(xstream.fromXML(serialValue), Student.class);
		
		System.out.println(list2.get(0).getName());
		
		//MAP
		Map<Integer, String> map1 = new HashMap<Integer, String>();
		map1.put(1, "11");
		map1.put(2, "22");
		serialValue = xstream.toXML(map1);
		System.out.println(serialValue);
		Map<Integer, String> map2 = toMap(xstream.fromXML(serialValue), Integer.class, String.class);
		
		System.out.println(map2.get(1));
		
		//枚举
		Color color = Color.RED;
		serialValue = xstream.toXML(color);
		System.out.println(serialValue);
		Color color2 = (Color)xstream.fromXML(serialValue);
		System.out.println(color2);
		
		//注入测试
		serialValue = xstream.toXML(new Student("</name><wang>yuan</wang>", 20));
		System.out.println(serialValue);
		Student student3 = (Student)xstream.fromXML(serialValue);
		System.out.println(student3.getName());
		
		serialValue = xstream.toXML(new Grade());
		System.out.println(serialValue);
		Grade g = (Grade)xstream.fromXML(serialValue);
		System.out.println(g.name);
	}
	
	private static <E> List<E> toList(Object obj, Class<E> clazz){
		return ReflectUtil.castList(List.class.cast(obj), clazz);
	}
	
	private static <K, V> Map<K, V> toMap(Object obj, Class<K> keyClazz, Class<V> valueClazz){
		return ReflectUtil.castMap(Map.class.cast(obj), keyClazz, valueClazz);
	}

}
