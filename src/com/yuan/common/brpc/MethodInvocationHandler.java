package com.yuan.common.brpc;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.BufferOverflowException;

import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import com.yuan.common.compress.GZIPTool;

public class MethodInvocationHandler implements InvocationHandler, Serializable {
   
	private static final long serialVersionUID = 1L;
	private static final InvocationSerializer invocationSerializer = SerializerFactory.newInvocationSerializer();
	
    private String className; 
    private String host;
    private int port;
    private Integer compressionMinSize;

    public MethodInvocationHandler(String host, int port, String className, Integer compressionMinSize) {
        this.host = host;
        this.port = port;
        this.className = className;
        this.compressionMinSize = compressionMinSize;
    }

    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        InvocationResponse response;
        IBlockingConnection bc = null;
        
        try {
            InvocationRequest request = new InvocationRequest(className, method, args);

            bc = new BlockingConnection(host, port);
			
            byte[] data = invocationSerializer.serial(request);
            send(bc, data);

            byte[] responseData = receive(bc);

            response = invocationSerializer.unserial(responseData, InvocationResponse.class);

        } catch (IOException e) {
            throw new RemotingException(e);
        }finally{
        	if(bc != null){
        		bc.close();
        	}
        }

        if (response.getException() != null)
            throw response.getException();

        return response.getResult();
    }
    private void send(IBlockingConnection bc, byte[] data) throws BufferOverflowException, IOException{
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
    private byte[] receive(IBlockingConnection bc) throws IOException{
    	Long responseLength = bc.readLong();
        byte isCompress = bc.readByte();
        byte[] responseData = bc.readBytesByLength(responseLength.intValue() - 1);   
        if(isCompress == 1){
        	responseData = GZIPTool.uncompress(responseData);
        }
        return responseData;
    }

	public String getClassName() {
		return className;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
    
}