package com.yuan.common.oxm;

public class ShortMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Short.parseShort(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
