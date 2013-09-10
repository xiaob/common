package com.yuan.common.xml;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.yuan.common.util.DateUtil;

public class DateAdapter extends XmlAdapter<String, Date> {

	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public Date unmarshal(String v) throws Exception {
		return DateUtil.parse(v, DATE_PATTERN);
	}

	@Override
	public String marshal(Date v) throws Exception {
		return DateUtil.format(v, DATE_PATTERN);
	}

}
