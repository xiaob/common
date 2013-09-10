package com.yuan.common.xml;

import java.util.Date;

public class Property {
	
	private String name;
	public String value;
	public String description;
	public Date date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return "["+name+", "+value+", "+description+"]";
	}

}
