package com.yuan.common.om;

public class DoubleMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Double.parseDouble(value);
	}
	public String encode(Object value) {
		return value.toString();
	}
	
}
