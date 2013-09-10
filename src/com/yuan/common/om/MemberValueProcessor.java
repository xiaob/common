package com.yuan.common.om;

public interface MemberValueProcessor {

	public String encode(Object value);
	
	public Object decode(String value);
	
}
