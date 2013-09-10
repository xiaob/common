package com.yuan.common.om;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractObjectMapper<E> implements ObjectMapper<E> {

	protected Map<String, MemberValueProcessor> memberValueProcessorMap = new HashMap<String, MemberValueProcessor>();
	protected OmFilter filter;
	protected MemberProcessor memberProcessor = null;
	
	public AbstractObjectMapper(MemberProcessor memberProcessor){
		this.memberProcessor = memberProcessor;
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
	public void registFilter(OmFilter filter){
		this.filter = filter;
	}
	
}
