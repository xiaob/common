package com.yuan.common.om;

public class IntegerMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Integer.parseInt(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
