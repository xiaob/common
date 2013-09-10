package com.yuan.common.oxm;

public class DoubleMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Double.parseDouble(value);
	}
	public String encode(Object value) {
		return value.toString();
	}
	
}
