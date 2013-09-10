package com.yuan.common.oxm;

public class CharacterMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return new Character(value.charAt(0));
	}
	public String encode(Object value) {
		return value.toString();
	}

}
