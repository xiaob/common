package com.yuan.common.om;

public class BooleanMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Boolean.parseBoolean(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
