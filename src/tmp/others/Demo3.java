package tmp.others;

import java.util.Calendar;


public class Demo3 {

	public static void main(String[] args) throws Exception {
		test2();
	}
	
	public static void test1(){
		Calendar c = Calendar.getInstance();
		c.set(2014, 0, 1);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		c.set(Calendar.MILLISECOND, 0);
		
		System.out.println(c.getTimeInMillis());
		System.out.println(c.getTime().toLocaleString());
	}
	
	public static void test2(){
		System.out.println(Long.MAX_VALUE + 1);
		System.out.println(Long.MAX_VALUE + 2);
	}

	public void t1(int a){
		System.out.println(a);
	}
	public void t2(String a){
		System.out.println(a);
	}
}

