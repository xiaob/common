package com.yuan.common.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DH安全编码组件(非对称加密, 密钥一致协议的加密算法)
 * DH算法是第一个密钥协商算法， 但仅能用于密钥分配， 不能用于加密或解密消息。
 * Diffie-Hellman算法(D-H算法)，密钥一致协议。是由公开密钥密码体制的奠基人Diffie和Hellman所提出的一种思想。
 * 简单的说就是允许两名用户在公开媒体上交换信息以生成"一致"的、可以共享的密钥。换句话说，就是由甲方产出一对密钥（公钥、私钥），
 * 乙方依照甲方公钥产生乙方密钥对（公钥、私钥）。以此为基线，作为数据传输保密基础，同时双方使用同一种对称加密算法构建本地密钥（SecretKey）
 * 对数据加密。这样，在互通了本地密钥（SecretKey）算法后，甲乙双方公开自己的公钥，使用对方的公钥和刚才产生的私钥加密数据
 * ，同时可以使用对方的公钥和自己的私钥对数据解密。
 * 不单单是甲乙双方两方，可以扩展为多方共享数据通讯，
 * 这样就完成了网络交互数据的安全通讯！
 * 该算法源于中国的同余定理——中国馀数定理
 * @author 
 * @version 1.0
 * @since 1.0
 */
public class DHCipher {
	public static final String ALGORITHM = "DH";

	/**
	 * 默认密钥字节数
	 * 
	 * <pre>
	 * DH 
	 * Default Keysize 1024   
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * DH加密下需要一种对称加密算法对数据加密，这里我们使用AES，也可以使用其他对称加密算法。
	 */
	public static final String SECRET_ALGORITHM = "AES";
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";

	/**
	 * 初始化甲方密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 甲方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 甲方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 初始化乙方密钥
	 * 
	 * @param key
	 *            甲方公钥
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String key) throws Exception {
		// 解析甲方公钥
		byte[] keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 由甲方公钥构建乙方密钥
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();

		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(keyFactory.getAlgorithm());
		keyPairGenerator.initialize(dhParamSpec);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 乙方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 乙方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}

	/**
	 * 加密<br>
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            本地密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
		// 数据加密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            本地密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// 生成本地密钥
		SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
		// 数据解密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 构建密钥
	 * 
	 * @param publicKey
	 *            公钥
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey)
			throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
				.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);

		// 生成本地密钥
		SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);

		return secretKey.getEncoded();
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
