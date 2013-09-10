package com.yuan.common.oxm;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.xml.Dom;

public abstract class AbstractOxm implements Oxm {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractOxm.class);
	
	protected Map<String, MemberValueProcessor> memberValueProcessorMap = new HashMap<String, MemberValueProcessor>();
	protected OxmFilter filter;
	
	public AbstractOxm(){
		init();
	}
	
	protected void init(){
		registMemberValueProcessor(Byte.class.getName(), new ByteMemberValueProcessor());
		registMemberValueProcessor(Byte.TYPE.getName(), new ByteMemberValueProcessor());
		registMemberValueProcessor(Short.class.getName(), new ShortMemberValueProcessor());
		registMemberValueProcessor(Short.TYPE.getName(), new ShortMemberValueProcessor());
		registMemberValueProcessor(Integer.class.getName(), new IntegerMemberValueProcessor());
		registMemberValueProcessor(Integer.TYPE.getName(), new IntegerMemberValueProcessor());
		registMemberValueProcessor(Long.class.getName(), new LongMemberValueProcessor());
		registMemberValueProcessor(Long.TYPE.getName(), new LongMemberValueProcessor());
		registMemberValueProcessor(Float.class.getName(), new FloatMemberValueProcessor());
		registMemberValueProcessor(Float.TYPE.getName(), new FloatMemberValueProcessor());
		registMemberValueProcessor(Double.class.getName(), new DoubleMemberValueProcessor());
		registMemberValueProcessor(Double.TYPE.getName(), new DoubleMemberValueProcessor());
		registMemberValueProcessor(Boolean.class.getName(), new BooleanMemberValueProcessor());
		registMemberValueProcessor(Boolean.TYPE.getName(), new BooleanMemberValueProcessor());
		registMemberValueProcessor(Character.class.getName(), new CharacterMemberValueProcessor());
		registMemberValueProcessor(Character.TYPE.getName(), new CharacterMemberValueProcessor());
		
		registMemberValueProcessor(Date.class.getName(), new DateMemberValueProcessor());
		registMemberValueProcessor("[B", new ByteArrayMemberValueProcessor());
		registMemberValueProcessor("[Ljava.lang.Byte;", new ByteArray2MemberValueProcessor());
	}
	public void registMemberValueProcessor(String className, MemberValueProcessor memberValueProcessor){
		memberValueProcessorMap.put(className, memberValueProcessor);
	}
	public void registFilter(OxmFilter filter){
		this.filter = filter;
	}

	@Override
	public <T> List<T> toObject(String xml, Class<T> clazz) {
		try {
			List<T> objList = new ArrayList<T>();
			
			Dom root = Dom.getRoot(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			List<Dom> objectElementList = root.elements("object");
			for(Dom objectElement : objectElementList){
				T obj = clazz.newInstance();
				List<Dom> memberElementList = objectElement.elements();
				for(Dom memberElement : memberElementList){
					String memberName = memberElement.getElementName();
					if((filter != null) && (!filter.isMatch(memberName))){
						continue;
					}
					String type = memberElement.getAttributeValue("type");
					String value = memberElement.getElementText();
					Object memberValue = null;
					if(memberValueProcessorMap.containsKey(type)){
						memberValue = memberValueProcessorMap.get(type).decode(value);
					}else if(!value.equals("null")){
						memberValue = value;
					}
					
					setMemeberValue(obj, memberName, memberValue);
				}
				objList.add(obj);
			}
			return objList;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
	
	protected abstract void setMemeberValue(Object obj, String memberName, Object memberValue);
	
	public String toXml(List<? extends Object> objList) {
		Dom dom = Dom.newDom("objectList");
		for(Object obj : objList){
			Dom objectElementDom = dom.addElement("object");
			List<? extends Member> memberList = getMembers(obj);
			for(Member member : memberList){
				String memberName = getMemberName(member);
				if((filter != null) && (!filter.isMatch(memberName))){
					continue;
				}
				String value = "";
				Object memberValue = getMemberValue(obj, member);
				if(memberValueProcessorMap.containsKey(getMemberType(member))){
					value = memberValueProcessorMap.get(getMemberType(member)).encode(memberValue);
				}else{
					if(memberValue == null){
						value = "null";
					}else{
						value = memberValue.toString();
					}
				}
				objectElementDom.addElement(memberName, value).setAttribute("type", getMemberType(member));
			}
			
		}
		return dom.toString();
	}
	
	protected abstract List<? extends Member> getMembers(Object obj);
	protected abstract Object getMemberValue(Object obj, Member member);
	protected abstract String getMemberType(Member member);
	protected abstract String getMemberName(Member member);

}
