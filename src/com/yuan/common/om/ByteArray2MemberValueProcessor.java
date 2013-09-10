package com.yuan.common.om;

import org.apache.commons.codec.binary.Base64;

public class ByteArray2MemberValueProcessor implements MemberValueProcessor {

	public Object decode(String value) {
		byte[] data = Base64.decodeBase64(value);
		Byte[] v = new Byte[data.length];
		for(int i=0; i<data.length; i++){
			v[i] = data[i];
		}
		return v;
	}

	public String encode(Object value) {
		Byte[] data = (Byte[])value;
		byte[] v = new byte[data.length];
		for(int i=0; i<data.length; i++){
			v[i] = data[i];
		}
		return Base64.encodeBase64String(v);
	}

}
