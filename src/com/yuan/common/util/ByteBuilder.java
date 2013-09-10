package com.yuan.common.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 和ByteBuffer类相比，该类的优势是长度可变， 而ByteBuffer的容量是固定的。
 * @author yuan
 *
 */
public class ByteBuilder {

	private List<Byte> list = new ArrayList<Byte>();
	
	public ByteBuilder(){
		
	}
	
	public ByteBuilder(byte b){
		list.add(b);
	}
	
	public ByteBuilder(byte[] bs){
		for(byte b : bs){
			list.add(b);
		}
	}
	
	public void append(byte b){
		list.add(b);
	}
	
	public void append(byte[] bs){
		if(bs == null){
			return ;
		}
		for(byte b : bs){
			list.add(b);
		}
	}
	
	public void append(byte[] bs, int startIndex, int length){
		if(bs == null){
			return ;
		}
		for(int i=startIndex; i<length; i++){
			list.add(bs[i]);
		}
	}
	
	public void append(ByteBuffer buf){
		for(int i=0; i<buf.limit(); i++){
			list.add(buf.get(i));
		}
	}
	
	public void append(ByteBuffer buf, int length){
		for(int i=0; i<length; i++){
			list.add(buf.get());
		}
	}
	
	public byte[] toByte(){
		byte[] data = new byte[list.size()];
		for(int i=0; i<list.size(); i++){
			data[i] = list.get(i);
		}
		return data;
	}
	
	public ByteBuffer toByteBuffer(){
		return ByteBuffer.wrap(toByte());
	}
	
	public void clear(){
		list.clear();
	}
	
	public int size(){
		return list.size();
	}
	
	public String toHex(){
		StringBuilder hex = new StringBuilder();
		byte[] data = toByte();
		for(byte b : data){
			String h = Integer.toHexString(b);
			if(h.length() == 1){
				hex.append("0");
			}
			hex.append(h).append(" ");
		}
		return hex.toString();
	}
	
	public static void main(String args[])throws Exception{
		Charset charset = Charset.forName("GBK");
		ByteBuffer bb = charset.encode("测试");
		CharBuffer cb = charset.decode(bb);
		
		System.out.println(cb.toString());
		System.out.println(Charset.isSupported("GBK"));
		System.out.println(Charset.isSupported("UTF-16BE"));
		
		byte[] data = new byte[1];
		ByteBuilder builder = new ByteBuilder();
		builder.append(data);
		System.out.println(builder.toHex());
	}
	
}
