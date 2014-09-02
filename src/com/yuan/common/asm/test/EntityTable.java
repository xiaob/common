package com.yuan.common.asm.test;

import java.util.List;


//@Entity
public class EntityTable  {

	public String entityName; //实体名
	public String title; //标题
	public String description; //说明
	public String validScript; //校验脚本
	public int layout = 1; //布局. 1. 左右表格列
	public String companyId; //企业标识
	public String appId; //应用标识
	public String version; //应用版本
	
//	@OneToMany
	public List<PropertyType> propertyList;
	
	public EntityTable(){
		super();
	}

	public EntityTable(String entityName, String title, String description,
			String validScript) {
		super();
		this.entityName = entityName;
		this.title = title;
		this.description = description;
		this.validScript = validScript;
	}
	
	
	
}
