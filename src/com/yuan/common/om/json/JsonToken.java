package com.yuan.common.om.json;

public enum JsonToken {
	
	START_OBJECT("{"),
	END_OBJECT("}"),
	START_ARRAY("["),
	END_ARRAY("]"),
	START_STRING("\""),
	END_STRING("\""),
	MEMBER_SEPARATOR(":"),
	ELEMENT_SEPARATOR(",");
	
	private String value;
	
	private JsonToken(String v){
		this.value = v;
	}
	
	public String value(){
		return value;
	}
	public Character charValue(){
		return value.charAt(0);
	}
}
