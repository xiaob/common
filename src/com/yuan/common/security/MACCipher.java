package com.yuan.common.security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * MAC消息摘要组件
 * MAC算法结合了MD5和SHA算法的优势, 并加入了密钥的支持, 是一种更为安全的消息摘要算法. 
 * @author yuan
 *
 */
public class MACCipher {
	
	public static byte[] initHmacMD5Key() throws Exception{
		// 初始化KeyGenerator
		KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
		//产生密钥
		SecretKey secretKey = kg.generateKey();
		//获得密钥
		return secretKey.getEncoded();
	}
	
	public static byte[] encodeHmacMD5(byte[] data, byte[] key)throws Exception{
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return mac.doFinal(data);
	}
	
	public static String hmacmd5Hex(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacMD5(data, key));
	}
	
	public static byte[] initHmacSHA512Key()throws Exception{
		// 初始化KeyGenerator
		KeyGenerator kg = KeyGenerator.getInstance("HmacSHA512");
		//产生密钥
		SecretKey secretKey = kg.generateKey();
		//获得密钥
		return secretKey.getEncoded();
	}
	
	public static byte[] encodeHmacSHA512(byte[] data, byte[] key)throws Exception{
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return mac.doFinal(data);
	}
	
	public static String hmacsha512Hex(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacSHA512(data, key));
	}
}
