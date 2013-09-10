package com.yuan.common.security;

import java.security.Provider;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TestSecurity {

	public static void main(String[] args)throws Exception {
//		testHex();
		testMD5();
//		testPBE();
//		testPrimary();
//		testDH();
//		testRSA();
//		testDSA();
	}
	
	public static void print(){
		Provider provider = new BouncyCastleProvider();
		Set<Map.Entry<Object,Object>> set = provider.entrySet();
		for(Map.Entry<Object,Object> entry : set){
			System.out.println(entry.getKey() + " , " + entry.getValue());
		}
	}
	
	public static void testHex()throws Exception{
		//十六进制, 编码后体积变为原来的2倍
		String data = Hex.encodeHexString("中国".getBytes());
		System.out.println(data);
		byte[] output = Hex.decodeHex(data.toCharArray());
		System.out.println(new String(output));
		
		//BASE64, 编码后体积变为原来的4/3倍;
		String baseData = Base64.encodeBase64String("编码".getBytes());
		System.out.println(baseData);
		byte[] out2 = Base64.decodeBase64(baseData);
		System.out.println(new String(out2));
		
		String url = "file:///E:/%E5%B8%AE%E5%8A%A9%E6%96%87%E4%BB%B6/%E5%85%B6%E5%AE%83%E5%BC%80%E6%BA%90%E5%BA%93/commons-codec-1.4-bin/commons-codec-1.4/apidocs/index.html";
		System.out.println(Base64.encodeBase64URLSafeString(url.getBytes()));
	}
	
	//消息摘要, 用于验证数据完整性
	public static void testMD5()throws Exception{
		String data = DigestUtils.md5Hex("MD5编码".getBytes());
		System.out.println(data + " " + data.length());
		String shaData = DigestUtils.sha512Hex("SHA编码".getBytes());
		System.out.println(shaData + " " + shaData.length());
		String macmd5Data = MACCipher.hmacmd5Hex("HmacMD5编码".getBytes(), MACCipher.initHmacMD5Key());
		System.out.println(macmd5Data);
		String macsha512Data = MACCipher.hmacsha512Hex("HmacSHA512编码".getBytes(), MACCipher.initHmacSHA512Key());
		System.out.println(macsha512Data);
		String crc32Data = CRC32Cipher.crc32Hex("CRC32编码".getBytes());
		System.out.println(crc32Data);
		System.out.println();
	}
	
	//对称加密
	public static void testPBE()throws Exception{
		byte[] salt = PBECipher.initSalt();
		String password = "123";
		String d = "PBE";
		
		byte[] data = PBECipher.encrypt(d.getBytes(), password, salt);
		System.out.println(Base64.encodeBase64String(data));
		
		byte[] r = PBECipher.decrypt(data, password, salt);
		System.out.println(new String(r));
		System.out.println();
		
	}
	
	//初等数据加密
	public static void testPrimary()throws Exception{
		SymmetricalCipher cipher = null;
		
		cipher = SymmetricalCipher.getDESInstace();
		System.out.println("DES密钥: " + cipher.getKeyString());
		byte[] desData = cipher.encrypt("DES加密".getBytes());
		System.out.println("密文： " + Base64.encodeBase64String(desData));
		
		byte[] desr = cipher.decrypt(desData);
		System.out.println("明文： " + new String(desr));
		
		cipher = SymmetricalCipher.getDESedeInstance();
		System.out.println("DESede密钥: " + cipher.getKeyString());
		byte[] desedeData = cipher.encrypt("DESede加密".getBytes());
		System.out.println("密文： " + Base64.encodeBase64String(desedeData));
		byte[] desedeText = cipher.decrypt(desedeData);
		System.out.println("明文： " + new String(desedeText));
		
		cipher = SymmetricalCipher.getAESInstace();
		System.out.println("AES密钥: " + cipher.getKeyString());
		
		byte[] data = cipher.encrypt("AES加密".getBytes());
		System.out.println(Base64.encodeBase64String(data));
		
		byte[] r = cipher.decrypt(data);
		System.out.println(new String(r));
	}
	
	//DH算法是第一个密钥协商算法， 但仅能用于密钥分配， 不能用于加密或解密消息
	public static void testDH()throws Exception{
		Map<String, Object> keyMap1 = DHCipher.initKey();
		byte[] publicKey1 = DHCipher.getPublicKey(keyMap1);
		byte[] privateKey1 = DHCipher.getPrivateKey(keyMap1);
		
		
		Map<String, Object> keyMap2 = DHCipher.initKey();
		byte[] publicKey2 = DHCipher.getPublicKey(keyMap2);
		byte[] privateKey2 = DHCipher.getPrivateKey(keyMap2);
		
		byte[] key1 = DHCipher.getSecretKey(publicKey2, privateKey1);
		System.out.println("甲方本地密钥: \n" + Base64.encodeBase64String(key1));
		
		byte[] key2 = DHCipher.getSecretKey(publicKey1, privateKey2);
		System.out.println("已方本地密钥: \n" + Base64.encodeBase64String(key2));
		
		byte[] data = "DH密钥交换算法".getBytes();
		byte[] cipherText = DHCipher.encrypt(data, key1);
		System.out.println("密文: " + Base64.encodeBase64String(cipherText));
		
		byte[] simpleText = DHCipher.decrypt(cipherText, key2);
		System.out.println("明文: " + new String(simpleText));
	}
	
	//非对称加密
	//RSA算法即包含加密/解密算法， 同时兼有数字签名算法
	public static void testRSA()throws Exception{
		Map<String, Object> keyMap = RSACipher.initKey();
		byte[] privateKey = RSACipher.getPrivateKey(keyMap);
		byte[] publicKey = RSACipher.getPublicKey(keyMap);
		
		System.out.println("privateKey = " + Base64.encodeBase64String(privateKey));
		System.out.println("publicKey = " + Base64.encodeBase64String(publicKey));
		
		byte[] data = "RSA数字签名".getBytes();
		byte[] sign = RSACipher.sign(data, privateKey);
		System.out.println("签名: " + Hex.encodeHexString(sign));
		
		boolean status = RSACipher.verify(data, publicKey, sign);
		System.out.println("状态： " + status);
		
		byte[] cipherText = RSACipher.encryptByPublicKey(data, publicKey);
		System.out.println("密文: " + Base64.encodeBase64String(cipherText));
		
		byte[] simpleText = RSACipher.decryptByPrivateKey(cipherText, privateKey);
		System.out.println("明文： " + new String(simpleText));
	}
	//数字签名 = 非对称加密 + 消息摘要
	//DSA算法仅包含数字签名算法， 使用DSA算法的数字证书无法进行加密通信
	public static void testDSA()throws Exception{
		Map<String, Object> keyMap = DSACipher.initKey();
		byte[] privateKey = DSACipher.getPrivateKey(keyMap);
		byte[] publicKey = DSACipher.getPublicKey(keyMap);
		
		System.out.println("privateKey = " + Base64.encodeBase64String(privateKey));
		System.out.println("publicKey = " + Base64.encodeBase64String(publicKey));
		
		byte[] data = "DSA数字签名".getBytes();
		byte[] sign = DSACipher.sign(data, privateKey);
		System.out.println("签名: " + Hex.encodeHexString(sign));
		
		boolean status = DSACipher.verify(data, publicKey, sign);
		System.out.println("状态： " + status);
	}

}
