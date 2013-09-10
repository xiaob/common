package com.yuan.common.aop.asmimpl;

public class ProxyClassLoader extends ClassLoader {

	private byte[] bytecode;
	
	public ProxyClassLoader(ClassLoader parent, byte[] bytecode){
		super(parent);
		this.bytecode = bytecode;
	}
	
	protected Class<?> findClass(String name)throws ClassNotFoundException{
		return super.defineClass(name, bytecode, 0, bytecode.length);
	}
	
}
