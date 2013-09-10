package com.yuan.common.sigar;




public class Test {
	
	public void go(){
//		this.getClass().getClassLoader().loadClass("");
	}

	public static void main(String[] args)throws Exception {
		LocalSystem.printAdapterInfoes();
		log(LocalSystem.getCurrentPid() + "");
		log(LocalSystem.getCurrentPname());
		log(LocalSystem.getCurrentPcpu() + "");
	}
	
	public static void log(String s){
		System.out.println(s);
	}

}
