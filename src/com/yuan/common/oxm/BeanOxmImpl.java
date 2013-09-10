package com.yuan.common.oxm;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.yuan.common.util.ReflectUtil;

public class BeanOxmImpl extends AbstractOxm {

	protected String getMemberType(Member member) {
		Method method = (Method)member;
		
		return method.getReturnType().getName();
	}
	
	protected String getMemberName(Member member){
		String name = member.getName().substring(3);
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return name;
	}

	protected Object getMemberValue(Object obj, Member member) {
		
		try {
			return ReflectUtil.execMethod(obj, member.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected List<? extends Member> getMembers(Object obj) {
		List<Method> list = new ArrayList<Method>();
		Method[] method = obj.getClass().getMethods();
		for(int i=0;i<method.length;i++){
			Method m = method[i];
			String methodName = m.getName();
			if((methodName.length()>3) && methodName.startsWith("get") && !methodName.equals("getClass")){
				list.add(m);
			}//if
			
		}//for
		return list;
	}

	protected void setMemeberValue(Object obj, String memberName,
			Object memberValue) {
		String name = "set" + Character.toUpperCase(memberName.charAt(0)) + memberName.substring(1);
		try {
			ReflectUtil.execMethod(obj, name, memberValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
