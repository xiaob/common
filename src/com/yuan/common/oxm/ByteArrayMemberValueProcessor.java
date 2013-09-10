package com.yuan.common.oxm;

import org.apache.commons.codec.binary.Base64;

public class ByteArrayMemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		
		return Base64.decodeBase64(value);
	}

	public String encode(Object value) {
		byte[] data = (byte[])value;
		
		return Base64.encodeBase64String(data);
	}

}
