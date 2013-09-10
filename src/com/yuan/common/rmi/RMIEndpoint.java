package com.yuan.common.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.ReflectUtil;

public class RMIEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(RMIEndpoint.class);
			
	private static ConcurrentMap<String, Object> serviceMap = new ConcurrentHashMap<String, Object>();
	private static String HOSTNAME;
	
	public static void addService(Object service){
		serviceMap.put(service.getClass().getName(), service);
	}
	public static void addService(Class<?> iface, Object service){
		serviceMap.put(iface.getName(), service);
	}
	
	/**
	 * 服务端多个IP则必须设置
	 * @param hostName
	 */
	public static void setHostName(String hostName){
		System.setProperty("java.rmi.server.hostname" , hostName); 
		HOSTNAME = hostName;
	}
	
	public static void publish() throws RemoteException, MalformedURLException, UnknownHostException{
		publish(1099);
	}
	public static void publish(int port) throws RemoteException, MalformedURLException, UnknownHostException{
		LocateRegistry.createRegistry(port); //注册端口
		
		GenericRmiService rmiService = new GenericRmiService(){
			private static final long serialVersionUID = 1L;

			public Object doService(String serviceName, String methodName, Object[] args) throws RemoteException {
				if(serviceMap.containsKey(serviceName)){
					Object service = serviceMap.get(serviceName);
					try {
						return ReflectUtil.execMethod(service, methodName, args);
					} catch (Exception e) {
						LOG.warn(e.getMessage(), e);
						throw new RemoteException(e.getMessage(), e);
					}
				}
				LOG.warn("RMI服务" + serviceName + "不存在!");
				throw new RemoteException("RMI服务" + serviceName + "不存在!");
			}
		};
		UnicastRemoteObject.exportObject(rmiService, 0); //随机通信端口
		if(HOSTNAME == null){
			HOSTNAME = InetAddress.getLocalHost().getHostAddress();
		}
		Naming.rebind("rmi://"+HOSTNAME+":"+port+"/GenericRmiService", rmiService);
	}
	
}
