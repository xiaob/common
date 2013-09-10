package com.yuan.common.oxm;

public interface MemberValueProcessor {

	public String encode(Object value);
	
	public Object decode(String value);
	
}
