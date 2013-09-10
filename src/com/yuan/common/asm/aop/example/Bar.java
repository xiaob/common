package com.yuan.common.asm.aop.example;


public interface Bar{
	public void b()throws Throwable;
	public int c(int p, int p1);
	public long d(long p, long p1);
	public float e(float p, float p1);
	public double f(double p, double p1);
	public String g(String g, String p1);
	public Object h(Object x1, Object x2);
	public Object i(int x1, char x2, byte x3, short x4, boolean x5, long x6, double x7,float x8, double[] x9, Object x10);
}

class BarImpl implements Bar{

	public void b()throws Throwable{
		try {
			System.out.println("bbbbbbb");
		} catch (Throwable e) {
		}
	}
	public int c(int p, int p1){
		System.out.println("cccccc");
		return p + p1;
	}
	public void d(String n){
//		name = "wwww" + n; 
	}
	
	public long d(long p, long p1){
		return 0l;
	}
	public float e(float p, float p1){
		return 0.0f;
	}
	public double f(double p, double p1){
		return 0.0;
	}
	public String g(String p, String p1){
		return "";
	}
	
	public Object h(Object x1, Object x2){
		return null;
	}
	public Object i(int x1, char x2, byte x3, short x4, boolean x5, long x6, double x7,float x8, double[] x9, Object x10){
		return null;
	}
}
