package tmp.others;

import java.math.BigDecimal;


public class Demo3 {

	public static void main(String[] args) throws Exception {
		double a = 0.1 + 0.1 + 0.1;

		System.out.println(a);
		
		BigDecimal b = new BigDecimal("0.1").add(new BigDecimal("0.1")).add(new BigDecimal("0.1"));
		
		System.out.println(b);
	}

}
