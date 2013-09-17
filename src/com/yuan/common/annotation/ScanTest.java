package com.yuan.common.annotation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class ScanTest {

	public static void main(String[] args) throws Exception {
		scan("tmp");
	}
	
	public static void scan(String basePackage)throws Exception{
		ClassPathScanner classPathScanner = new ClassPathScanner();
		Set<Class<?>> classSet = classPathScanner.getPackageAllClasses(basePackage, true);
		for(Class<?> clazz : classSet){
			System.out.println(clazz.getName());
		}
		
		Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>();
		// 对象实例化
		for(Class<?> clazz : classSet){
			Resource classAnnotation = clazz.getAnnotation(Resource.class);
			if(classAnnotation != null){
				Object bean = clazz.newInstance();
				beanMap.put(clazz, bean);
			}
		}
		
		// 注入
		for(Class<?> clazz : classSet){
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				Resource fieldAnnotation = f.getAnnotation(Resource.class);
				if(fieldAnnotation != null){
					Class<?> resourceType = f.getType();
					if(beanMap.containsKey(resourceType)){
						PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
						Method method = pd.getWriteMethod();
						method.invoke(beanMap.get(clazz), beanMap.get(resourceType));
					}
				}
			}
		}
		
		// 初始化
		for(Class<?> clazz : classSet){
			Method[] methods = clazz.getDeclaredMethods();
			for(Method m : methods){
				PostConstruct methodAnnotation = m.getAnnotation(PostConstruct.class);
				if(methodAnnotation != null){
					m.invoke(beanMap.get(clazz));
				}
			}
		}
	}

}
