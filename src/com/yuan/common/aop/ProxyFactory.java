package com.yuan.common.aop;

import java.io.IOException;

public interface ProxyFactory {
	
	public void setSuperClass(Class<?> superClass);
	public void addDelegate(Class<?> interfaceClass, Object interfaceDelegateObject);
	public void addAreacut(Areacut areacut);
	public Object getProxy();
	public Class<?> createProxyClass();
	public void writeToFile(String dir)throws IOException;

}
