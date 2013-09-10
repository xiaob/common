package com.yuan.common.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DSA安全编码组件(数字签名, 非对称加密)
 * DSA算法仅包含数字签名算法， 使用DSA算法的数字证书无法进行加密通信。 而RSA算法即包含加密/解密算法， 同时兼有数字签名算法。
 * DSA-Digital Signature Algorithm 是Schnorr和ElGamal签名算法的变种，被美国NIST作为DSS(DigitalSignature Standard)。
 * 简单的说，这是一种更高级的验证方式，用作数字签名。不单单只有公钥、私钥，还有数字签名。私钥加密生成数字签名，公钥验证数据及签名。
 * 如果数据和签名不匹配则认为验证失败！数字签名的作用就是校验数据在传输过程中不被修改。数字签名，是单向加密的升级！
 * @author 
 * @version 1.0
 * @since 1.0
 */
public class DSACipher {

	public static final String ALGORITHM = "DSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";
	
	/**
	 * 默认密钥字节数
	 * 
	 * <pre>
	 * DSA  
	 * Default Keysize 1024   
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * 默认种子
	 */
	private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

	private static final String PUBLIC_KEY = "DSAPublicKey";
	private static final String PRIVATE_KEY = "DSAPrivateKey";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initSign(priKey);
		signature.update(data);

		return signature.sign();
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign)
			throws Exception {

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

		// ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(sign);
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 *            种子
	 * @return 密钥对象
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String seed) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
		// 初始化随机产生器
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(seed.getBytes());
		keygen.initialize(KEY_SIZE, secureRandom);

		KeyPair keys = keygen.genKeyPair();

		DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();

		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);

		return map;
	}

	/**
	 * 默认生成密钥
	 * 
	 * @return 密钥对象
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		return initKey(DEFAULT_SEED);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return key.getEncoded();
	}
}