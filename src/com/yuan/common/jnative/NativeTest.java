package com.yuan.common.jnative;

import java.util.Date;

public class NativeTest {
	
	public static void main(String args[]){
		System.out.println("1. " + new Date());
		JNative jNative = NativeFactory.newNative();
		jNative.setLocalTime(new Date(System.currentTimeMillis() + (10*60*1000))); //10分钟
		System.out.println("2. " + new Date());
	}

}
