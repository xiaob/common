package com.yuan.common.asm.aop.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import com.yuan.common.aop.asmimpl.AsmProxyFactoryImpl;

public class TreeTest {
	
	public static void print(Object obj){
		Method[] ms = obj.getClass().getMethods();
		for(Method m : ms){
			System.out.println(m.getName());
		}
	}
	
	public static void test(AsmProxyFactoryImpl proxyFactory)throws Throwable{
		Object obj = proxyFactory.getProxy();
		System.out.println(obj);
//		A a = (A)obj;
//		a.a();
		
		Bar bar = (Bar)obj;
		bar.b();
		bar.i(0, ' ', (byte)0, (short)0, true, 0l, 0.0, 0.0f, new double[]{0.0}, null);
//		
		Car c = (Car)obj;
		c.x1();
	}
	
	public static void main(String args[])throws Exception{
		AsmProxyFactoryImpl proxyFactory = new AsmProxyFactoryImpl();
		proxyFactory.setClassName("AProxy");
//		proxyFactory.setSuperClass(Test1.class);
		proxyFactory.addDelegate(Bar.class, new BarImpl());
		proxyFactory.addDelegate(Car.class, new CarImpl());
		
//		test(proxyFactory);
//		write(proxyFactory);
		
		
	}
	
	public void write(AsmProxyFactoryImpl proxyFactory){
		w(proxyFactory.getProxyClassData(), "d:/"+proxyFactory.getProxyClassShortName()+".class");
	}
	
	private static void w(byte[] data, String file){
		try {
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(data);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}


