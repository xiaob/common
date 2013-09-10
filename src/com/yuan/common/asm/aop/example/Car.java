package com.yuan.common.asm.aop.example;

public interface Car {
	
	public void x1();
//	public Object x2(Object obj);

}

class CarImpl implements Car{
	
	public void x1(){
		System.out.println("xxxxxxxxxxxxxxx");
	}
	
	public Object x2(Object obj){
		return null;
	}
}
