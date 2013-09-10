package com.yuan.common.brpc;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.Server;

import com.yuan.common.compress.GZIPTool;
import com.yuan.common.util.ReflectUtil;

public class ServiceEndpoint {

	public static Integer compressionMinSize = 2048; //2KB
	private static Map<String, Object> serviceCache = new ConcurrentHashMap<String, Object>();
	private static Server server;
	private static ClassLoader classLoader;
	static{
		if(Thread.currentThread().getContextClassLoader() != null){
			classLoader = Thread.currentThread().getContextClassLoader();
		}else{
			classLoader = ServiceEndpoint.class.getClassLoader();
		}
	}
	
	public static void setClassLoader(ClassLoader loader){
		classLoader = loader;
	}
	public static Class<?> loadClass(String className) throws ClassNotFoundException{
		return classLoader.loadClass(className);
	}
	
	public static void addService(Object service){
		serviceCache.put(service.getClass().getName(), service);
	}
	
	public static void addService(Class<?> serviceInterface, Object service){
		serviceCache.put(serviceInterface.getName(), service);
	}
	
	public static Object getService(String name){
		return serviceCache.get(name);
	}
	
	public static void publish(int port) throws UnknownHostException, IOException{
		server = new Server(port, new ServerHandler(compressionMinSize));
		server.start();
	}
	
	public static void close(){
		if(server != null){
			server.close();
		}
	}
	
}

class ServerHandler implements IDataHandler,IConnectHandler,IDisconnectHandler,IIdleTimeoutHandler  { 
	
	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	private static final InvocationSerializer invocationSerializer = SerializerFactory.newInvocationSerializer();
	private Map<INonBlockingConnection, RpcRequest> requestCache = new ConcurrentHashMap<INonBlockingConnection, RpcRequest>();
	private Integer compressionMinSize;
	
	public ServerHandler(Integer compressionMinSize){
		this.compressionMinSize = compressionMinSize;
	}
	
	public boolean onConnect(INonBlockingConnection nbc) throws IOException { 
		return true;
	}
	
	public boolean onDisconnect(INonBlockingConnection nbc) throws IOException { 
		nbc.close();
		requestCache.remove(nbc);
		log.debug("请求结束：" + nbc);
		return true;
	}
	
	public boolean onData(INonBlockingConnection nbc)throws IOException, BufferUnderflowException, MaxReadSizeExceededException { 
		if(!nbc.isOpen()){
			return true;
		}
		log.debug("[" + nbc.getRemoteAddress().getHostAddress() + ":" + nbc.getRemotePort() + "] available = " + nbc.available());
		
		InvocationResponse invocationResponse = null;
        InvocationRequest invocationRequest = null;
        Method method = null;
        Object result = null;
        try{
        	if(!collectData(nbc)){//收集数据
        		return true;
        	}
    		byte[] requestData = requestCache.get(nbc).getData(); //获取收集完整的数据
    		
        	invocationResponse = new InvocationResponse();
            invocationRequest = invocationSerializer.unserial(requestData, InvocationRequest.class);
            
            Object service = null;
            if(ServiceEndpoint.getService(invocationRequest.getClassName()) != null){
            	service = ServiceEndpoint.getService(invocationRequest.getClassName());
            }else{
            	service = ServiceEndpoint.loadClass(invocationRequest.getClassName()).newInstance();
            	ServiceEndpoint.addService(service);
            }
            
            method = ReflectUtil.getMethod(service.getClass(), invocationRequest.getMethodName(), invocationRequest.getParameterTypes());

            result = method.invoke(service, invocationRequest.getParameters());
            invocationResponse.setResult((Serializable) result);
        }catch (Exception e) {
        	log.warn(e.getMessage(), e);
        	if (e instanceof InvocationTargetException) {
                InvocationTargetException ite = (InvocationTargetException) e;
                invocationResponse.setException(ite.getTargetException());
            } else {
                if (method != null && method.getExceptionTypes() != null) {
                    for (Class<?> exType : method.getExceptionTypes()) {
                        if (exType.isAssignableFrom(e.getClass()))
                            invocationResponse.setException(e);
                    }
                }
                invocationResponse.setException(new RemotingException(e));
            }
		}finally{
			try {
                if (invocationRequest != null) {
                    byte[] responseData = invocationSerializer.serial(invocationResponse);
                    send(nbc, responseData);
                    nbc.close();
                } else {//请求数据还没有收集完整或者发生异常了
//                    log.warn("请求为  NULL ！！！");
                }
            } catch (Exception e) {
            	log.warn(e.getMessage(), e);
                InvocationResponse reporter = new InvocationResponse();
                reporter.setException(new RuntimeException(e.getClass() + " 写响应错误: " + e.getMessage()));

                try {
					byte[] responseData = invocationSerializer.serial(reporter);
					send(nbc, responseData);
					nbc.close();
				} catch(Exception e1) {
					log.warn(e1.getMessage(), e1);
				}
            }
		}
		return true;
	}
	private void send(INonBlockingConnection bc, byte[] data) throws BufferOverflowException, IOException{
    	byte isCompress = 0;
        long length = data.length + 1;
        if(data.length >= compressionMinSize.intValue()){
        	data = GZIPTool.compress(data);
        	isCompress = 1;
        	length = data.length + 1;
        }
        bc.write(length);
    	bc.write(isCompress);
    	bc.write(data);
        bc.flush();
    }

	private boolean collectData(INonBlockingConnection nbc) throws IOException{
		if(requestCache.containsKey(nbc)){
			RpcRequest request = requestCache.get(nbc);
			int remaining = request.remaining();
			int available = nbc.available();
			if(remaining <= available){
				request.put(nbc.readBytesByLength(remaining));
				return true;
			}else{
				request.put(nbc.readBytesByLength(available));
				return false;
			}
		}
		Long requestLength = nbc.readLong();
    	RpcRequest request = new RpcRequest(requestLength.intValue());
    	requestCache.put(nbc, request);
    	int remaining = request.remaining();
		int available = nbc.available();
		if(remaining <= available){
			request.put(nbc.readBytesByLength(remaining));
			return true;
		}else if(available > 0){
			request.put(nbc.readBytesByLength(available));
			return false;
		}
		return false;
	}
	
	public boolean onIdleTimeout(INonBlockingConnection connection)throws IOException{
		return true;

	}
	
}

class RpcRequest{
	private ByteBuffer buffer;
	
	public RpcRequest(int capacity){
		buffer = ByteBuffer.allocate(capacity);
	}
	public void put(byte[] data){
		buffer.put(data);
	}
	public byte[] getRawData(){
		return buffer.array();
	}
	public byte[] getData() throws IOException{
		buffer.rewind();
		byte isCompress = buffer.get();
		byte[] data = new byte[buffer.limit() - buffer.position()];
		buffer.get(data);
		if(isCompress == 1){
			data = GZIPTool.uncompress(data);
		}
		return data;
	}
	public int remaining(){
		return buffer.remaining();
	}
}
