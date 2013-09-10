package com.yuan.common.oxm;

public class FloatMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Float.parseFloat(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
