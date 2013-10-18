package com.yuan.common.udp;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PacketBuffer {

	private ByteBuffer buf;
	
	public PacketBuffer(int size){
		buf = ByteBuffer.allocate(size);
	}
	
	public PacketBuffer(byte[] data){
		buf = ByteBuffer.wrap(data);
	}
	
	public int readInt(){
		return buf.getInt();
	}
	
	public void writeInt(int data){
		buf.putInt(data);
	}
	
	public long readLong(){
		return buf.getLong();
	}
	
	public void writeLong(long data){
		buf.putLong(data);
	}
	
	public String readString(){
		int len = buf.getInt();
		switch (len) {
		case -1:
			return null;
		case 0:
			return "";
		default:
			byte[] data = new byte[len];
			buf.get(data);
			try {
				return new String(data, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void writeString(String str){
		if(str == null){
			buf.putInt(-1);
			return ;
		}
		if(str.equals("")){
			buf.putInt(0);
			return ;
		}
		try {
			byte[] data = str.getBytes("UTF-8");
			buf.putInt(data.length);
			buf.put(data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public DatagramPacket build(){
		for(int i = buf.position(); i<buf.limit(); i++){
			buf.put((byte)0);
		}
		return new DatagramPacket(buf.array(), buf.limit());
	}
}
