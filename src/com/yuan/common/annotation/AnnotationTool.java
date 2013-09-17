package com.yuan.common.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationTool {
	
	private String className;

	public static void main(String[] args)throws Exception {
		AnnotationTool tool = new AnnotationTool("com.yuan.common.annotation.Testing");
		tool.parse();
	}
	
	public AnnotationTool(String className) {
		super();
		this.className = className;
	}

	public void parse()throws Exception {
		Class<?> clazz = Class.forName(className);
		parseClass(clazz);
		Method[] methods = clazz.getDeclaredMethods();
		parseMethod(methods);
		Field[] fields = clazz.getDeclaredFields();
		parseField(fields);
	}
	
	public void parseClass(Class<?> clazz){
		MyClass classAnnotation = clazz.getAnnotation(MyClass.class);
		if(classAnnotation != null){
			System.out.println(clazz.toString() + " : " +classAnnotation);
		}
	}
	
	public void parseMethod(Method[] methods){
		for(Method m : methods){
			MyMethod methodAnnotation = m.getAnnotation(MyMethod.class);
			if(methodAnnotation != null){
				System.out.println(m.toGenericString() + " : "+methodAnnotation);
			}
		}
	}
	
	public void parseField(Field[] fields){
		for(Field f : fields){
			MyField fieldAnnotation = f.getAnnotation(MyField.class);
			if(fieldAnnotation != null){
				System.out.println(f.toGenericString()+" : "+fieldAnnotation);
			}
		}
	}

}
