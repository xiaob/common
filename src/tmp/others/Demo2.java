package tmp.others;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;

public class Demo2 {

	public static void main(String[] args) {
		Calendar c1 = Calendar.getInstance();
		c1.set(2013, 0, 1, 0, 0, 0);
		c1.set(Calendar.MILLISECOND, 0);
		System.out.println(c1.getTimeInMillis());
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2047, 0, 1, 0, 0, 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		long time = c2.getTimeInMillis() - c1.getTimeInMillis();
		
		System.out.println(Long.toBinaryString(time).length());
	}
	
	public static void test2(){
		long time  = System.currentTimeMillis();
		System.out.println(time);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 0, 1, 0, 0, 0);
		System.out.println(calendar.getTime().getTime());
//		System.out.println(calendar.getTime().toLocaleString());
	}
	
	public static void test1(){
		String password = "test1";
		String data = encode(password);
		
		System.out.println(data);
		System.out.println(data.length());
	}
	public static String encode(String password){
		return DigestUtils.md5Hex(reverse(password) + password + reverse(password)).toLowerCase();
	}
	private static String reverse(String s){
		
		return new StringBuilder(s).reverse().toString();
	}

}
