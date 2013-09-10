package com.yuan.common.brpc;

public class RemotingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RemotingException(Exception e) {
        super(e);
    }
	
	public RemotingException(String message){
		super(message);
	}
	
}
