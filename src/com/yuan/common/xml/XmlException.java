package com.yuan.common.xml;

public class XmlException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XmlException(String message){
		super(message);
	}
	
	public XmlException(String message, Throwable cause){
		super(message, cause);
	}
}
