package com.yuan.common.om.binary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

public class JsonTest {

	public static void main(String[] args)throws Exception {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//		mapper.registerSubtypes(Student.class);
		SerializationConfig sc = mapper.getSerializationConfig();
        sc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setSerializationConfig(sc);

		//对象
		String serialValue = mapper.writeValueAsString(new Student("wang", 20));
		System.out.println(serialValue);
		Student student = mapper.readValue(serialValue, Student.class);
		System.out.println(student.getName());
		
		//数组
		String[] arr1 = {"111", "222"};
		serialValue = mapper.writeValueAsString(arr1);
		System.out.println(serialValue);
		String[] arr2 = mapper.readValue(serialValue, String[].class);
		System.out.println(arr2[0]);
		
		//集合
		List<Student> list1 = new ArrayList<Student>();
		list1.add(new Student("wang", 20));
		list1.add(new Student("zhang", 21));
		serialValue = mapper.writeValueAsString(list1);
		System.out.println(serialValue);
		List<Student> list2 = mapper.readValue(serialValue, new TypeReference<List<Student>>(){});
		System.out.println(list2.get(0).getName());
		
		//MAP
		Map<Integer, String> map1 = new HashMap<Integer, String>();
		map1.put(1, "11");
		map1.put(2, "22");
		serialValue = mapper.writeValueAsString(map1);
		System.out.println(serialValue);
		Map<Integer, String> map2 = mapper.readValue(serialValue, new TypeReference<Map<Integer, String>>(){});
		System.out.println(map2.get(1));
		
		//枚举
		Color color = Color.RED;
		serialValue = mapper.writeValueAsString(color);
		System.out.println(serialValue);
		Color color2 = mapper.readValue(serialValue, Color.class);
		System.out.println(color2);
		
		//注入测试 
		serialValue = mapper.writeValueAsString(new Student("\",{wang}[]:,\"</html>", 20));
		System.out.println(serialValue);
		Student student3 = mapper.readValue(serialValue, Student.class);
		System.out.println(student3.getName());
		
		serialValue = mapper.writeValueAsString(new Grade());
		System.out.println(serialValue);
		Grade g = mapper.readValue(serialValue, Grade.class);
		System.out.println(g.name);
		
		System.out.println("时间： " + mapper.writeValueAsString(new Date()));
	}

}
