package com.yuan.common.om.json;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import com.yuan.common.om.AbstractObjectMapper;
import com.yuan.common.om.BeanMemberProcessor;
import com.yuan.common.om.MemberProcessor;
import com.yuan.common.util.StringUtil;

public class JsonObjectMapperImpl extends AbstractObjectMapper<String> {

	public JsonObjectMapperImpl(){
		super(new BeanMemberProcessor());
	}
	public JsonObjectMapperImpl(MemberProcessor memberProcessor){
		super(memberProcessor);
	}
	
	@Override
	public <T> T readObject(String source, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> readObjectList(String source, Class<T> clazz) {
		return null;
	}
	
	public <K extends Object, V extends Object> Map<K, V> readObjectMap(String source, Class<K> kClass, Class<V> vClass){
		
		return null;
	}

	@Override
	public String writeObject(Object obj) {
		StringBuilder json = new StringBuilder();
		json.append(JsonToken.START_OBJECT);
		List<? extends Member> memberList = memberProcessor.getMembers(obj);
		for(Member member : memberList){
			String memberName = memberProcessor.getMemberName(member);
			if((filter != null) && (!filter.isMatch(obj.getClass().getName(), memberName))){
				continue;
			}
//			String value = "";
//			Object memberValue = memberProcessor.getMemberValue(obj, member);
//			if(memberValueProcessorMap.containsKey(memberProcessor.getMemberType(member))){
//				value = memberValueProcessorMap.get(memberProcessor.getMemberType(member)).encode(memberValue);
//			}else{
//				if(memberValue == null){
//					value = "null";
//				}else{
//					value = memberValue.toString();
//				}
//			}
//			objectElementDom.addElement(memberName, value).setAttribute("type", getMemberType(member));
//			
//			json.append("\"").append(memberName).append("\":");
//			Object obj = propertyMap.get(propertyName);
//			appendValue(json, obj, jsonFormat);
//			json.append(",");
		}
		json = StringUtil.compareAndDeleteLastChar(json, JsonToken.ELEMENT_SEPARATOR.charValue());
		json.append(JsonToken.END_OBJECT);
		
		return json.toString();
	}
	
	public String writeObject(Object[] objArr){
		
		return null;
	}

	@Override
	public String writeObject(List<? extends Object> objList) {
		return null;
	}
	
	public String writeObject(Map<? extends Object, ? extends Object> objMap){
		
		return null;
	}
	
	protected String serialValue(Object obj, Member member){
//		if(obj == null){
//			return "null";
//		}else if(memberValueProcessorMap.containsKey(memberProcessor.getMemberType(member))){
//			return memberValueProcessorMap.get(memberProcessor.getMemberType(member)).encode(obj);
//		}else if(isString(obj)){
//			json.append("\"").append(obj).append("\"");
//			
//		}else if(isNumber(obj) || isBoolean(obj)){
//			json.append(obj);
//		}else if(obj instanceof Date){
//			json.append("\"").append(DateUtil.format((Date)obj, "yyyy-MM-dd HH:mm:ss.SSS")).append("\"");
//		}else if(obj.getClass().isArray()){
//			json.append(arrayToJSON((Object[])obj, jsonFormat.getSubFormat()));
//		}else if(obj instanceof List){
//			json.append(listToJSON((List<Object>)obj, jsonFormat.getSubFormat()));
//		}else if(obj instanceof Map){
//			json.append(mapToJSON((Map<String, Object>)obj, jsonFormat.getSubFormat()));
//		}else{
//			json.append(new JsonBean(obj).toJSON(jsonFormat.getSubFormat()));
//		}
		return "";
	}

}
