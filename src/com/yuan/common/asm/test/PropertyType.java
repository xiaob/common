package com.yuan.common.asm.test;

public class PropertyType {
	
	public Long value;
	public String textValue;

	public PropertyType(){
		
	}

	public PropertyType(Long value, String textValue) {
		super();
		this.value = value;
		this.textValue = textValue;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
}
