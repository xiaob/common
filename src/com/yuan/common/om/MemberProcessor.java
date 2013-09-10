package com.yuan.common.om;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberProcessor {
	
	public void setMemeberValue(Object obj, String memberName, Object memberValue);
	public List<? extends Member> getMembers(Object obj);
	public Object getMemberValue(Object obj, Member member);
	public String getMemberType(Member member);
	public String getMemberName(Member member);
	
}
