package com.yuan.common.asm.test;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.List;

import com.yuan.common.asm.ClassUtil;
import com.yuan.common.util.ReflectUtil;

public class Test2 {

	public static void main(String[] args)throws Exception {
		test3();
	}
	
	public static void test1()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
//		Field field = ClassLoader.class.getDeclaredField("classes");
//		field.setAccessible(true);// 设置该成员变量为可访问
//		System.out.println(field.get(ClassLoader.getSystemClassLoader()));
		System.out.println(getLoadClasses(ClassLoader.getSystemClassLoader()));

	}
	public static List<Object> getLoadClasses(ClassLoader classLoader)throws Exception{
		Field field = ClassLoader.class.getDeclaredField("classes");
		field.setAccessible(true);// 设置该成员变量为可访问
		Object obj = field.get(classLoader);
		if(obj != null) {
			return ReflectUtil.castList(obj, Object.class);
		}
		
		return null;
	}
	
	public static void test2(){
		System.out.println(ClassUtil.getInternalPackageName(String.class));
	}
	
	public static void test3()throws Exception{
//		ClassUtil.trace(new FileInputStream("E:\\dev\\workspace\\hxbos\\src\\trunk\\modules\\core\\brpc\\代理接口生成工具\\bin\\mycompile\\proxy\\ITestCopileAPI.class"));
		ClassUtil.trace(new FileInputStream("D:\\tmp\\bin\\mycompile\\TestCopileAPI.class"));
	}

}
