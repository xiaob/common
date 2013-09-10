package com.yuan.common.oxm;

public class ByteMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Byte.parseByte(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
