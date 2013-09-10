package com.yuan.common.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;


public class BinaryTool {
	
	public static void main(String args[]){
//		byte b = 50;
//		System.out.println(Integer.toBinaryString(b));
//		System.out.println(Integer.toBinaryString(50));
//		System.out.println(Integer.toHexString(2));
//		System.out.println(Integer.toBinaryString(3));
//		System.out.println(Integer.parseInt("00110000", 2));
//		Integer i = 3;
//		System.out.println(i.byteValue());
		
		byte[] buf = toByte(10000);
		System.out.println(buf[0] + ", " + buf[1] + ", " + buf[2] + ", " + buf[3]);
		System.out.println(concat(buf[0], buf[1], buf[2], buf[3]));
	}
	
	public static String additionalLeftZero(String binaryString, int length){
		StringBuilder sb = new StringBuilder();
		int zeroNum = length - binaryString.length();
		for(int i=0; i<zeroNum; i++){
			sb.append("0");
		}
		sb.append(binaryString);
		
		return sb.toString();
	}
	public static String additionalRightZero(String binaryString, int length){
		StringBuilder sb = new StringBuilder();
		sb.append(binaryString);
		int zeroNum = length - binaryString.length();
		for(int i=0; i<zeroNum; i++){
			sb.append("0");
		}
		return sb.toString();
	}
	public static String hightBitBinary(byte b){
		String bs = Integer.toBinaryString(b);
		if(bs.length() <= 4){
			return "0000";
		}
		
		return bs.substring(0, bs.length() - 4);
	}
	
	public static String lowerBitBinary(byte b){
		String bs = Integer.toBinaryString(b);
		String lower = bs;
		if(bs.length() > 4){
			lower = bs.substring(bs.length() - 4);
		}
		return lower;
	}
	/**
	 * 高四位的值
	 * @param b byte
	 * @return int
	 */
	public static int hightBit(byte b){
		return Integer.parseInt(hightBitBinary(b), 2);
	}
	
	public static int lowerBit(byte b){
		return Integer.parseInt(lowerBitBinary(b), 2);
	}
	
	/**
	 * 将b1的低四位作为高位, 将b2的低四位作为低位连接为一个字节
	 * @param b1 byte
	 * @param b2 byte
	 * @return byte
	 */
	public static byte concat(byte b1, byte b2){
		String lower1 = additionalLeftZero(lowerBitBinary(b1), 4);
		String lower2 = additionalLeftZero(lowerBitBinary(b2), 4);
		Integer v = Integer.parseInt((lower1 + lower2), 2);
		return v.byteValue();
	}
	
	public static int concat(byte b1, byte b2, byte b3, byte b4){
		String l1 = additionalLeftZero(toBinaryString(b1), Byte.SIZE);
		String l2 = additionalLeftZero(toBinaryString(b2), Byte.SIZE);
		String l3 = additionalLeftZero(toBinaryString(b3), Byte.SIZE);
		String l4 = additionalLeftZero(toBinaryString(b4), Byte.SIZE);
		
		Long v = Long.parseLong(l1 + l2 + l3 + l4, 2);
		return v.intValue();
	}
	public static int concatInt(ByteBuffer buf){
		String l1 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l2 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l3 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l4 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		
		Long v = Long.parseLong(l1 + l2 + l3 + l4, 2);
		return v.intValue();
	}
	public static int concatInt(byte[] data){
		int value = 0;
		for(int i=0; i<data.length; i++){
			value = (value << Byte.SIZE) | (data[i] & 0xff); //防止符号位扩展
		}
		return value;
	}
	
	public static long concat(byte[] data){
		ByteBuffer buf = ByteBuffer.wrap(data);
		
		return concatLong(buf);
	}
	public static long concatLong(byte[] data){
		long value = 0;
		for(int i=0; i<data.length; i++){
			value = (value << Byte.SIZE) | (data[i] & 0xff); //防止符号位扩展
		}
		return value;
	}
	public static long concatLong(ByteBuffer buf){
		String l1 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l2 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l3 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l4 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l5 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l6 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l7 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		String l8 = additionalLeftZero(toBinaryString(buf.get()), Byte.SIZE);
		
		BigInteger v = new BigInteger(l1 + l2 + l3 + l4 + l5 + l6 + l7 + l8, 2);
		return v.longValue();
	}
	
	/**
	 * 将一个整数转换成4个字节的字节数组
	 * @param number
	 * @return byte[]
	 */
	public static byte[] toByte(int number){
		byte[] buf = new byte[4];
		String binaryString = additionalLeftZero(Integer.toBinaryString(number), Integer.SIZE);
		
		for(int i=0; i<4; i++){
			int beginIndex = i * 8;
			int endIndex = beginIndex + 8;
			buf[i] = toByte(binaryString.substring(beginIndex, endIndex));
		}
		return buf;
	}
	
	/**
	 * 将一个长整数转换成8个字节的字节数组
	 * @param number long
	 * @return byte[]
	 */
	public static byte[] toByte(long number){
		byte[] buf = new byte[8];
		String binaryString = additionalLeftZero(Long.toBinaryString(number), Long.SIZE);
		for(int i=0; i<8; i++){
			int beginIndex = i * 8;
			int endIndex = beginIndex + 8;
			buf[i] = toByte(binaryString.substring(beginIndex, endIndex));
		}
		return buf;
	}
	
	public static byte toByte(String binaryString){
		if(binaryString.length() > Byte.SIZE){
			throw new IllegalArgumentException(binaryString + ". ");
		}
		
		Short v = Short.parseShort(binaryString, 2);
		return v.byteValue();
	}
	public static String toBinaryString(byte b){
		String binaryString = Integer.toBinaryString(b);
		
		if(binaryString.length() > Byte.SIZE){
			binaryString = binaryString.substring(binaryString.length() - 8, binaryString.length());
		}
		return binaryString;
	}
	
}
