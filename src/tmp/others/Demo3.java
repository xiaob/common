package tmp.others;

import java.util.Calendar;


public class Demo3 {

	public static void main(String[] args) throws Exception {
		
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

}
