package com.yuan.common.om;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.yuan.common.util.AssertUtil;
import com.yuan.common.util.ReflectUtil;

public class FieldMemberProcessor implements MemberProcessor {

	@Override
	public String getMemberName(Member member) {
		return member.getName();
	}

	@Override
	public String getMemberType(Member member) {
		Field field = (Field)member;
		return field.getType().getName();
	}

	@Override
	public Object getMemberValue(Object obj, Member member) {
		try {
			return ReflectUtil.getFieldValue(obj, member.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<? extends Member> getMembers(Object obj) {
		Field[] fieldList = obj.getClass().getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		if(AssertUtil.notEmpty(fieldList)){
			for(Field field : fieldList){
				if(Modifier.isPublic(field.getModifiers())){
					list.add(field);
				}
			}
		}
		return list;
	}

	@Override
	public void setMemeberValue(Object obj, String memberName,
			Object memberValue) {
		try {
			ReflectUtil.setFieldValue(obj, memberName, memberValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
