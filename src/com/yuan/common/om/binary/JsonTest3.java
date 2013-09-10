package com.yuan.common.om.binary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.yuan.common.util.ReflectUtil;

public class JsonTest3 {

	public static void main(String[] args) {
		String serialValue = JSON.toJSONString(new Student("wang", 20));
		System.out.println(serialValue);
		Student student = JSON.parseObject(serialValue, Student.class);
		System.out.println(student.getName());
		
		//数组
		String[] arr1 = {"111", "222"};
		serialValue = JSON.toJSONString(arr1);
		System.out.println(serialValue);
		String[] arr2 = JSON.parseObject(serialValue, String[].class);
		System.out.println(arr2[0]);
		
		//集合
		List<Student> list1 = new ArrayList<Student>();
		list1.add(new Student("wang", 20));
		list1.add(new Student("zhang", null));
		serialValue = JSON.toJSONString(list1, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue);
		System.out.println(serialValue);
		System.out.println(toList(JSON.parse(serialValue), Student.class).get(1).getName());
		
		List<Student> list2 = JSON.parseArray(serialValue, Student.class);
		System.out.println(list2.get(0).getName());
		
		//MAP
		Map<Integer, String> map1 = new HashMap<Integer, String>();
		map1.put(1, "11");
		map1.put(2, "22");
		serialValue = JSON.toJSONString(map1);
		System.out.println(serialValue);
		Map<Integer, String> map2 = JSON.parseObject(serialValue, new TypeReference<Map<Integer, String>>(){});
		System.out.println(map2.get(1));
		
		//枚举
		Color color = Color.RED;
		serialValue = JSON.toJSONString(color);
		System.out.println(serialValue);
		Color color2 = JSON.parseObject(serialValue, Color.class);
		System.out.println(color2);
		
		//注入测试 
		serialValue = JSON.toJSONString(new Student("\",{wang}[]:,\"</html>", 20));
		System.out.println(serialValue);
		Student student3 = JSON.parseObject(serialValue, Student.class);
		System.out.println(student3.getName());
		
		serialValue = JSON.toJSONString(new Grade());
		System.out.println(serialValue);
		Grade g = JSON.parseObject(serialValue, Grade.class);
		System.out.println(g.name);
		
		SerializeConfig mapping = new SerializeConfig(); 
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd")); 
		System.out.println("时间： " + JSON.toJSONString(new Date(), mapping));
	}

	private static <E> List<E> toList(Object obj, Class<E> clazz){
		return ReflectUtil.castList(List.class.cast(obj), clazz);
	}
}
