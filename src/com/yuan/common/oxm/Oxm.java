package com.yuan.common.oxm;

import java.util.List;

public interface Oxm {

	public String toXml(List<? extends Object> objList);
	
	public <T extends Object> List<T> toObject(String xml, Class<T> clazz);
	
	public void registMemberValueProcessor(String className, MemberValueProcessor memberValueProcessor);
	public void registFilter(OxmFilter filter);
	
}
