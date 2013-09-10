package com.yuan.common.security;

import java.util.zip.CRC32;

/**
 * CRC循环冗余校验. 生成的散列值在传输或储存之前计算出来并且附加到数据后面. 
 * 在使用数据之前, 对数据的完整性做校验.
 * @author yuan
 *
 */
public class CRC32Cipher {

	public static Long encode(byte[] data)throws Exception{
		CRC32 crc = new CRC32();
		crc.update(data);
		
		return crc.getValue();
	}
	
	public static String crc32Hex(byte[] data)throws Exception{
		return Long.toHexString(encode(data));
	}
}
