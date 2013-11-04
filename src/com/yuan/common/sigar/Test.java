package com.yuan.common.sigar;

import java.io.File;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yuan.common.util.SystemTool;




public class Test {
	
	public void go(){
//		this.getClass().getClassLoader().loadClass("");
	}

	public static void main(String[] args)throws Exception {
//		System.setProperty("org.hyperic.sigar.path", "d:/");
//		LocalSystem.printAdapterInfoes();
		log(LocalSystem.getCurrentPid() + "");
		log(LocalSystem.getCurrentPname());
		log(LocalSystem.getCurrentPcpu() + "");
		log(LocalSystem.getCpuUsageRate() + "");
		
		System.out.println("====" + System.getProperty("java.library.path"));
		System.out.println(SystemTool.getAppPath(Test.class));
	}
	
	public static void log(String s){
		System.out.println(s);
	}

}
