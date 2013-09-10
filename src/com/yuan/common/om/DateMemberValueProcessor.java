package com.yuan.common.om;

import java.util.Date;

import com.yuan.common.util.DateUtil;

public class DateMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		return DateUtil.parse(value, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	public String encode(Object value) {
		return DateUtil.format((Date)value, "yyyy-MM-dd HH:mm:ss.SSS");
	}

}
