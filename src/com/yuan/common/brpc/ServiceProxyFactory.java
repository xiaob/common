package com.yuan.common.brpc;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.ReflectUtil;


public class ServiceProxyFactory {
    
	private static final Logger log = LoggerFactory.getLogger(ServiceProxyFactory.class);
	
	private static Map<String, Object> proxyCache = new ConcurrentHashMap<String, Object>();
	public static Integer compressionMinSize = 2048; //2KB
	
    public static <T> T createProxy(Class<? extends T> serviceInterface, String host, int port) {
    	String serviceInterfaceName = serviceInterface.getName();
    	
    	if(proxyCache.containsKey(serviceInterfaceName)){
    		return serviceInterface.cast(proxyCache.get(serviceInterfaceName));
    	}
    	
    	try {
			String serviceClasName = getServiceClasName(serviceInterface);
			Object proxy = Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[] { serviceInterface }, new MethodInvocationHandler(host, port, serviceClasName, compressionMinSize));
			proxyCache.put(serviceInterfaceName, proxy);
			return serviceInterface.cast(proxy);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
    	return null;
    }
    private static String getServiceClasName(Class<?> serviceInterface){
    	Object className = null;
    	try {
			className = ReflectUtil.getStaticFieldValue(serviceInterface, "CLASSNAME");
		} catch (NoSuchFieldException e) {
			
		} catch (Exception e) {
	    	log.warn(e.getMessage(), e);
	    }
    	
    	if(className != null){
    		return (String)className;
    	}
    	return serviceInterface.getName();
    }

}