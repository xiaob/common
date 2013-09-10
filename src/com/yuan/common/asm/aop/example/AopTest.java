package com.yuan.common.asm.aop.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import com.yuan.common.aop.AfterAdvice;
import com.yuan.common.aop.Areacut;
import com.yuan.common.aop.AroundAdvice;
import com.yuan.common.aop.BeforeAdvice;
import com.yuan.common.aop.MethodInvocation;
import com.yuan.common.aop.PointcutImpl;
import com.yuan.common.aop.ProxyFactory;
import com.yuan.common.aop.asmimpl.AsmProxyFactoryImpl;
import com.yuan.common.aop.cglibimpl.CgLibProxyFactoryImpl;
import com.yuan.common.asm.ClassUtil;

public class AopTest {

	public static void main(String[] args)throws Throwable {
		test2();
	}
	
	public static void write(AsmProxyFactoryImpl proxyFactory){
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
	
	public static void test1()throws Throwable{
		AsmProxyFactoryImpl proxyFactory = new AsmProxyFactoryImpl();
		proxyFactory.setClassName("AProxy");
		proxyFactory.setSuperClass(A.class);
		proxyFactory.addDelegate(Bar.class, new BarImpl());
		proxyFactory.addDelegate(Car.class, new CarImpl());
//		proxyFactory.setAreacut(new Areacut(new PointcutImpl(), new AdviceImpl()));
		
		Object obj = proxyFactory.getProxy();
		A a = (A)obj;
		a.a();
		
		Bar bar = (Bar)obj;
		bar.b();
		
		Car car = (Car)obj;
		car.x1();
		
//		write(proxyFactory);
	}
	
	public static void test2(){
		ProxyFactory proxyFactory = new CgLibProxyFactoryImpl();
		proxyFactory.setSuperClass(A.class);
		proxyFactory.addAreacut(new Areacut(new PointcutImpl("a"), new LogAdvice()));
		proxyFactory.addAreacut(new Areacut(new PointcutImpl("a"), new TimeAdvice()));
		A proxy = (A)proxyFactory.getProxy();
		proxy.a();
		try {
			ClassUtil.trace(proxyFactory.createProxyClass());
//			proxyFactory.writeToFile("d:/test/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class LogAdvice implements BeforeAdvice, AfterAdvice{

	@Override
	public void afterReturning(Object returnObj, Method method, Object[] args, Object target)throws Throwable {
		System.out.println("Log after ... ...");
	}

	@Override
	public void before(Method method, Object[] args, Object target)throws Throwable {
		System.out.println("Log befroe ... ...");
	}

}

class TimeAdvice implements AroundAdvice{

	@Override
	public Object around(MethodInvocation invocation)throws Throwable {
		long start = System.currentTimeMillis();
		Object result = invocation.proceed();
		long end = System.currentTimeMillis();
		System.out.println("执行方法耗时：" + (end - start) + "毫秒");
		return result;
	}
	
}

