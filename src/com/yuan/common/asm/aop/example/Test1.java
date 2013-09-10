package com.yuan.common.asm.aop.example;

import javax.persistence.Entity;

import com.yuan.common.asm.AnnotationInfo;
import com.yuan.common.asm.ClassUtil;
import com.yuan.common.asm.DynamicClass;

public class Test1 {
	
	public static void main(String args[])throws Exception{
//		ClassUtil.trace(BarImpl.class);
//		ClassUtil.mifer(BarImpl.class);
//		ClassUtil.mifer(EntityTable.class);
//		ClassUtil.mifer(PropertyType.class);
//		ClassUtil.mifer(Testmain.class);
		
		test();
		
	}
	
	public static void test()throws Exception{
		DynamicClass dc = new DynamicClass("com/hx/User");
		dc.addClassAnnotation(new AnnotationInfo(Entity.class));
		dc.addField(String.class, null, "name");
//		dc.addField(List.class, PropertyType.class, "propertyList", OneToMany.class);
		
		dc.writeToFile("e:/test/User.class");
		ClassUtil.mifer(dc.getClassInputStream());
	}
	
	public static void test1(){
//		List<PropertyType> list = new ArrayList<PropertyType>();
//		Class<?> clazz = list.getClass();
//		TypeVariable<?>[] t = clazz.getTypeParameters();
//		System.out.println((Class<?>)t[0]);
	}
	
}
