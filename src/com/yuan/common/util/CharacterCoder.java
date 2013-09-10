package com.yuan.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CharacterCoder {
	
	public static void main(String args[])throws Exception{
//		InputStream is = new FileInputStream("d:/address.txt");
//		String test = EncodeUtil.base64Encode(is);
//		is.close();
//		System.out.println(test);
//		byte[] out = EncodeUtil.base64Decode(test);
//		FileOutputStream fos = new FileOutputStream("d:/tmp.txt");
//		fos.write(out);
//		fos.close();
//		System.out.println(getUnicodeEncoding("短信网关"));
//		System.out.println(getUnicodeEncoding("我的短信网关服务"));
		tryEncode("?gb2312?B?v6rUtM/uxL8=?=");
	}
	
	/**
	 * 将Unicode字符串转成汉字,如将 '\\u6d4b\\u8bd5' 转换为 '测试'
	 * @param oldValue String 要转换的字符串
	 * @return String
	 */
	public static String decodeUnicode(String oldValue){
		String decodeStr = "";
		String[] hexValue = oldValue.split("\\\\u");
		for(int i=1;i<hexValue.length;i++){
			String subStr = hexValue[i];
			char c = (char)Integer.parseInt(subStr, 16);
			decodeStr += c + "";
		}
		return decodeStr;
	}
	
	/**
	 * 尝试给字符串解码
	 * @param oldStr String 不知道编码的字符串
	 * @throws Exception
	 */
	public static void tryEncode(String oldStr)throws Exception{
		//字符集编码字典
		final String[] charset = {"GB2312","UTF-8","ISO-8859-1","ASCII","UTF-16","UTF-32","GBK","GB18030"};
		
		for(String encoding:charset){
			byte[] tmp = oldStr.getBytes(encoding);
			
//			String newStr = new String(tmp, encoding);
			String newStr = new String(tmp);
			System.out.println("用"+encoding+"解码: " + newStr);
		}
	
	}
	
	/**
	 * 将字符串转换为unicode标量值,如将 '测试' 转换为 '\u6d4b\u8bd5'
	 * @param str String
	 * @return String
	 */
	public static String getUnicodeEncoding(String str){
		String unicodeEncoding = "";
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			int intValue = (int)c;
			String hexValue = Integer.toHexString(intValue);
			unicodeEncoding += "\\u"+hexValue;
		}
		return unicodeEncoding;
	}
	
	public static String utf8ToIso(String oldValue){
		String newValue = null;
		try {
			byte[] data = oldValue.getBytes("UTF-8");
			newValue =  new String(data, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newValue;
	}
	
	public static String isoToUtf8(String oldValue){
		String newValue = null;
		try {
			byte[] data = oldValue.getBytes("ISO-8859-1");
			newValue = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newValue;
	}
	
	public static String toUTF8(String oldValue, String encoding){
		String newValue = null;
		byte[] data = null;
		try {
			data = oldValue.getBytes(encoding);
			newValue = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newValue;
	}
	
	public static String encodeUrl(String url){
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decodeUrl(String url){
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toGBK(String value){
		try {
			return new String(value.getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getSystemEncoding(){
		String encoding = System.getProperty("file.encoding");
		System.out.println("系统默认编码 : " + encoding);
		return encoding;
	}
	
	//Java class文件是UTF-8, 运行时是UTF-16BE
	//UTF-16BE: 双字节
	//US-ASCII: 单字节 
	//GBK: 单字节，双字节
	//GB18030: 单字节，双字节，四字节
	//UTF-8: 单字节，双字节, 三字节
	
	//UTF-16BE --> UCS2
	//UTF-16和UCS2都是双字节， 很相似。 在有些情况下UTF-16比UCS2多了两个字节的字序，当然这两个字节的字序对UTF-16是可选的，不是必须的。
	//而UCS2没有字序的规定。FEFF表示BE，FFFE表示LE
	/*
	 * UTF-8以字节为编码单元，没有字节序的问题。UTF-16以两个字节为编码单元，
	 * 在解释一个UTF-16文本前，首先要弄清楚每个编码单元的字节序。
	 * 例如“奎”的Unicode编码是594E，“乙”的Unicode编码是4E59。
	 * 如果我们收到UTF-16字节流“594E”，那么这是“奎”还是 “乙”？
	 * Unicode规范中推荐的标记字节顺序的方法是BOM(Byte Order Mark).
	 * 在UCS编码中有一个叫做"ZERO WIDTH NO-BREAK SPACE"的字符，它的编码是FEFF。
	 * 而FFFE在UCS中是不存在的字符，所以不应该出现在实际传输中。
	 * UCS规范建议我们在传输字节流前，先传输字符"ZERO WIDTH NO-BREAK SPACE"。
	 * 这样如果接收者收到FEFF，就表明这个字节流是Big-Endian的；
	 * 如果收到FFFE，就表明这个字节流是Little-Endian的。
	 * 因此字符"ZERO WIDTH NO-BREAK SPACE"又被称作BOM。
	 */
	public static byte[] encodeUCS2(String src){
		return encodeUTF16BE(src);
	}
	public static String decodeUCS2(byte[] src){
		return decodeUTF16BE(src);
	}
	
	public static byte[] encodeUTF16BE(String src){
		try {
			return src.getBytes("UTF-16BE");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String decodeUTF16BE(byte[] src){
		try {
			return new String(src, "UTF-16BE");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encodeUTF8(String src){
		try {
			return src.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String decodeUTF8(byte[] src){
		try {
			return new String(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//UTF-16BE --> GBK
	public static byte[] encodeGBK(String src){
		try {
			return src.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String decodeGBK(byte[] src){
		try {
			return new String(src, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	//UTF-16BE --> ASCII
	public static byte[] encodeASCII(String src){
		try {
			return src.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String decodeASCII(byte[] src){
		try {
			return new String(src, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

