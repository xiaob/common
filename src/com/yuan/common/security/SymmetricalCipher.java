package com.yuan.common.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/**
 * 初等数据加密, 对称加密
 * @author yuan
 *
 */
public class SymmetricalCipher {
	/** 
     * ALGORITHM 算法 <br> 
     * 可替换为以下任意一种算法，同时key值的size相应改变。 
     * <pre> 
     * DES                  key size must be equal to 56 
     * DESede(TripleDES)    key size must be equal to 112 or 168 
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
     * RC2                  key size must be between 40 and 1024 bits 
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
     * </pre> 
     */  
	protected String cipherAlgorithm = "DES/EBC/PKCS5Padding"; // 算法/工作模式/填充方式
	protected Key key;
  
    private SymmetricalCipher(String keyAlgorithm, String cipherAlgorithm, int keySize)throws Exception{
    	this.cipherAlgorithm = cipherAlgorithm;
    	key = initKey(keyAlgorithm, keySize);
    }
    
    public static SymmetricalCipher getDESInstace()throws Exception{
    	
    	return getDESInstace("ECB", "PKCS5Padding", 56);
    }
    public static SymmetricalCipher getDESInstace(String mode, String fill, int keySize)throws Exception{
    	return new SymmetricalCipher("DES", "DES/"+mode+"/"+fill, keySize);
    }
    
    public static SymmetricalCipher getDESedeInstance()throws Exception{
    	return getDESedeInstance("ECB", "PKCS5Padding", 168);
    }
    public static SymmetricalCipher getDESedeInstance(String mode, String fill, int keySize)throws Exception{
    	return new SymmetricalCipher("DESede", "DESede/"+mode+"/"+fill, keySize);
    }
    
    public static SymmetricalCipher getAESInstace()throws Exception{
    	
    	return getAESInstace("ECB", "PKCS5Padding", 256);
    }
    public static SymmetricalCipher getAESInstace(String mode, String fill, int keySize)throws Exception{
    	return new SymmetricalCipher("AES", "AES/"+mode+"/"+fill, keySize);
    }
    
    public Key getKey(){
    	return key;
    }
    
    public String getKeyString(){
    	return Base64.encodeBase64String(key.getEncoded());
    }
  
    /** 
     * 解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public byte[] decrypt(byte[] data) throws Exception {  
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);  
        cipher.init(Cipher.DECRYPT_MODE, key);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public byte[] encrypt(byte[] data) throws Exception {  
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
  
        return cipher.doFinal(data);  
    }  
  
    /** 
     * 生成密钥 
     *  
     * @param seed 
     * @return 
     * @throws Exception 
     */  
    private Key initKey(String keyAlgorithm, int keySize) throws Exception {  
        KeyGenerator kg = KeyGenerator.getInstance(keyAlgorithm);  
        kg.init(keySize);  
  
        SecretKey secretKey = kg.generateKey();  
        return secretKey;
    }  
}
