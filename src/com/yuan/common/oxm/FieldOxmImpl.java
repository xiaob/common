package com.yuan.common.oxm;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuan.common.util.AssertUtil;
import com.yuan.common.util.ReflectUtil;
import com.yuan.common.xml.Property;

public class FieldOxmImpl extends AbstractOxm {

	public FieldOxmImpl(){
		super();
	}
	
	protected String getMemberType(Member member) {
		Field field = (Field)member;
		return field.getType().getName();
	}
	
	protected String getMemberName(Member member){
		return member.getName();
	}

	protected Object getMemberValue(Object obj, Member member) {
		
		try {
			return ReflectUtil.getFieldValue(obj, member.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected List<? extends Member> getMembers(Object obj) {
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

	protected void setMemeberValue(Object obj, String memberName,
			Object memberValue) {
		try {
			ReflectUtil.setFieldValue(obj, memberName, memberValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]){
		List<Property> list = new ArrayList<Property>();
		Property p = new Property();
		p.setName("1111");
		p.setValue("2222");
		p.setDescription("3333");
		p.date = new Date();
		list.add(p);
		
		Oxm oxm = new FieldOxmImpl();
		oxm.registFilter(new OxmFilter() {
			public boolean isMatch(String memberName) {
				if(memberName.equals("value")){
					return false;
				}
				return true;
			}
		});
		String xml = oxm.toXml(list);
		System.out.println(xml);
		
		List<Property> list2 = new FieldOxmImpl().toObject(xml, Property.class);
		System.out.println(list2.get(0).date);
	}
	
}
