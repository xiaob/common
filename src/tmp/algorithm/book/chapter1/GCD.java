package tmp.algorithm.book.chapter1;

public class GCD {

	public static void main(String[] args) {
//		System.out.println(gcd(10, 5));
//		System.out.println(gcd(100, 10));
//		System.out.println(gcd(105, 24));
		System.out.println(gcd(1111111, 1234567));
	}
	
	// 最大公约数
	public static int gcd(int p, int q){
		System.out.println("p = " + p + ", q = " + q);
		if(q == 0) return p;
		int r = p % q;
		
		return gcd(q, r);
	}

}
