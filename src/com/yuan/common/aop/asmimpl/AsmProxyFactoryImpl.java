package com.yuan.common.aop.asmimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import com.yuan.common.aop.Areacut;
import com.yuan.common.asm.ClassUtil;
import com.yuan.common.util.ReflectUtil;

public class AsmProxyFactoryImpl {
	
	private String className;
	private Class<?> superClass;
	private List<Class<?>> interfaceList = new ArrayList<Class<?>>();
	private Map<Class<?>, Object> delegateObjectMap = new HashMap<Class<?>, Object>();
	private Areacut areacut;
	
	private String realProxyClassName;
	
	public void setClassName(String className){
		this.className = className;
	}
	
	public void setSuperClass(Class<?> superClass){
		this.superClass = superClass;
	}
	
	public void addDelegate(Class<?> ifClass, Object delegateObject){
		interfaceList.add(ifClass);
		delegateObjectMap.put(ifClass, delegateObject);
	}
	
	public void setAreacut(Areacut areacut){
		this.areacut = areacut;
	}
	
	public byte[] getProxyClassData(){
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		AddDelegateClassAdapter ad = new AddDelegateClassAdapter(cw, className, interfaceList, areacut);
		try {
			if(superClass == null){
				superClass = Object.class;
			}
			ClassReader classReader = new ClassReader(superClass.getName());
			classReader.accept(ad, ClassReader.SKIP_DEBUG);
		} catch (IOException e) {
			e.printStackTrace();
		}
		realProxyClassName = ad.getRealClassName();
		return cw.toByteArray();
	}
	
	public Object getProxy(){
		ProxyClassLoader pClassLoader = new ProxyClassLoader(this.getClass().getClassLoader(), getProxyClassData());
		try {
			Object proxyObject = pClassLoader.loadClass(realProxyClassName).newInstance();
			for(Class<?> interfaceClass : interfaceList){
				String methodName = "set" + ClassUtil.getShortName(interfaceClass);
				ReflectUtil.execMethod(proxyObject, methodName, delegateObjectMap.get(interfaceClass));
			}
			if((areacut != null) && (areacut.getAdvice() != null)){
				ReflectUtil.execMethod(proxyObject, "setAdvice", areacut.getAdvice());
			}
			return proxyObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProxyClassShortName(){
		int index = realProxyClassName.lastIndexOf(".");
		if(index == -1){
			return realProxyClassName;
		}
		return realProxyClassName.substring(index + 1);
	}
	
}
