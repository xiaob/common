package com.yuan.common.asm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ByteClassLoader extends AbstractClassLoader {

	private Map<String, byte[]> classDataMap = new LinkedHashMap<String, byte[]>();

	public ByteClassLoader(ClassLoader parent){
		super(parent);
	}
	public ByteClassLoader(ClassLoader parent, Map<String, byte[]> classDataMap){
		super(parent);
		this.classDataMap = classDataMap;
	}
	
	public List<Class<?>> loadAllClasses()throws ClassNotFoundException{
		List<Class<?>> classList = new ArrayList<Class<?>>();
		
		Set<String> classNameSet = classDataMap.keySet();
		for(String className : classNameSet){
			classList.add(super.loadClass(className));
		}
		return classList;
	}
	
	public void putClassData(String className, byte[] bytecode){
		classDataMap.put(className, bytecode);
	}
	
	protected byte[] loadClassData(String name) throws ClassNotFoundException{
		return classDataMap.get(name);
	}
	
}