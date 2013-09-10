package com.yuan.common.asm.test;

import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import com.yuan.common.asm.DynamicClass;
import com.yuan.common.util.ReflectUtil;

public class TestClass {

	
	public static void main(String[] args)throws Exception {
//		System.out.println(Type.getDescriptor(String.class));
//		System.out.println(Type.getInternalName(String.class));
//		System.out.println(Type.getObjectType("java/lang/Object").getClassName());
//		System.out.println(Type.INT_TYPE.getDescriptor());
//		System.out.println("java.lang.Object".replaceAll("\\.", "/"));
		test();
//		testJar();
	}
	
	public static void testJar()throws Exception{
		JarFile jarFile = new JarFile("E:\\play-1.0.3\\apps\\cxf\\lib\\jaxen-1.1.1.jar");
		Enumeration<JarEntry> e = jarFile.entries();
		while(e.hasMoreElements()){
			JarEntry entry = e.nextElement();
			System.out.println(entry.getName());
		}
	}
	
	public static void test(){
		DynamicClass dc = new DynamicClass("com/hx/User");
		dc.addField(String.class, null, "name");
		
		Class<?> clazz = dc.createClass();
		System.out.println("clazz.getName() = " + clazz.getName());
		System.out.println("clazz.getPackage() = " + clazz.getPackage());
	}
	
	public static void test1(){
		BeanGenerator bg = new BeanGenerator();
		bg.setSuperclass(Object.class);
		bg.addProperty("name", String.class);
		bg.addProperty("age", Integer.class);
		Object obj = bg.create();
		System.out.println(obj);
		BeanMap bm = BeanMap.create(obj);
		Set<String> set = ReflectUtil.castSet(bm.keySet(), String.class);
		for(String o : set){
			System.out.println(o + ", " + bm.get(o));
		}
		try {
			System.out.println(ReflectUtil.execMethod(obj, "getName"));
			ReflectUtil.execMethod(obj, "setName", "wwwwww");
			System.out.println(ReflectUtil.execMethod(obj, "getName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
