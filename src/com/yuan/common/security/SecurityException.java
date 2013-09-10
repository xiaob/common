package com.yuan.common.security;

public class SecurityException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SecurityException(String message, Throwable cause){
		super(message, cause);
	}

}
