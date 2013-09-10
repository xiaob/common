package com.yuan.common.om;

public class LongMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return Long.parseLong(value);
	}
	public String encode(Object value) {
		return value.toString();
	}

}
