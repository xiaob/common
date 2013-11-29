package tmp.algorithm.book.chapter1.section1;

import java.util.Arrays;

public class Test1 {

	public static void main(String[] args) {
//		test1();
//		test2();
//		String[] myargs = {"11", "22", "11"};
//		test3(myargs);
//		test5(0.2, 1.3);
//		test6();
//		test7();
//		test8();
//		test9();
//		test11();
//		test12();
//		test13();
//		System.out.println(lg(100));
//		int[] a = {1,2,3,4,4,2,5,9};
//		System.out.println(Arrays.toString(histogram(a, 3)));
//		System.out.println(exR1(6));
//		System.out.println(mystery(2, 25));
//		test19();
//		test20();
//		test21();
//		System.out.println(binomial(100, 50, 0));
		test30();
	}
	
	public static void test1(){
		System.out.println((0+15)/2);
		System.out.println(2.0e-6*100000000.1);
		System.out.println(true&&false||true&&true);
	}
	
	public static void test2(){
		System.out.println((1+2.236)/2);
		System.out.println(1+2+3+4.0);
		System.out.println(4.1>=4);
		System.out.println(1+2+"3");
	}
	
	public static void test3(String[] args){
		int i1 = Integer.parseInt(args[0]);
		int i2 = Integer.parseInt(args[1]);
		int i3 = Integer.parseInt(args[2]);
		
		if((i1 == i2) && (i2 == i3)){
			System.out.println("equal");
		}else{
			System.out.println("not equal");
		}
	}
	
	public static void test5(double x, double y){
		if((x > 0.0) && (x < 1.0) && (y > 0.0) && (y < 1.0)){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
	
	public static void test6(){
		int f = 0;
		int g = 1;
		for(int i=0; i<= 15; i++){
			System.out.println(f);
			f = f + g;
			g = f - g;
		}
	}
	
	public static void test7(){
		double t = 9.0;
		while(Math.abs(t - 9.0/t) > .001)
			t = (9.0/t + t) / 2.0;
		System.out.printf("%.5f\n", t);
		
		int sum = 0;
		for(int i=1; i< 1000; i++){
			for(int j=0; j<i; j++){
				sum ++;
			}
		}
		System.out.println(sum);
		
		int sum2 = 0;
		for(int i=1; i<1000; i++){
			for(int j=0; j< 1000; j++){
				sum2 ++;
			}
		}
		System.out.println(sum2);
	}
	
	public static void test8(){
		System.out.println('a');
		System.out.println('b' + 'c');
		System.out.println((char)('a' + 4));
	}
	
	public static void test9(){
		int N = 10;
		String s = "";
		for(int n=N; n>0; n/=2){
			s = (n % 2) + s;
		}
		System.out.println(s);
	}
	
	public static void test11(){
		boolean[][] bb = {{true, false}, {false, false}};
		
		for(int i=0; i< bb.length; i++){
			for(int j=0; j<bb[i].length; j++){
				String s  = bb[i][j] ? "*" : " ";
				System.out.println(i + ", " + j + " : " + s);
			}
		}
	}
	
	public static void test12(){
		int[] a = new int[10];
		for(int i=0; i<10; i++){
			a[i] = 9 -i;
		}
		for(int i=0; i< 10; i++){
			a[i] = a[a[i]];
		}
		for(int i=0; i<10; i++){
			System.out.println(i);
		}
	}
	
	public static void test13(){
		int[][] a = {{1,2},{3,4},{5,6}};
		int[][] b = new int[2][3];
		
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a[i].length; j++){
				// 行号和列号互换
				b[j][i] = a[i][j];
			}
		}
		
		System.out.println(Arrays.toString(b[0]));
		System.out.println(Arrays.toString(b[1]));
	}
	
	public static int lg(int N){
		int result = 0;
		
		int sum = 1;
		for(int i=1; true; i++){
			sum *= 2;
			if(sum > N){
				return result;
			}else{
				result = i;
			}
		}
	}
	
	public static int[] histogram(int[] a, int M){
		int[] b = new int[M];
		
		for(int i=0; i<M; i++){
			int count = 0;
			for(int j=0; j<a.length; j++){
				if(a[j] == i){
					count ++;
				}
			}
			b[i] = count;
		}
		
		return b;
	}
	
	public static String exR1(int n){
		if(n <=0){
			return "";
		}
		
		return exR1(n-3) + n + exR1(n-2) + n;
	}
	
	public static int mystery(int a, int b){
		if(b == 0){
			return 1;
		}
		
		if(b%2==0){
			return mystery(a*a, b/2);
		}
		
		return mystery(a*a, b/2)*a;
	}
	
	public static void test19(){
		for(int i=0; i<100; i++){
			System.out.println(fibonacci(i));
		}
	}
	
	public static long fibonacci(int N){
		if(N == 0){
			return 0;
		}
		if(N == 1){
			return 1;
		}
		
		long f1 = 0;
		long f2 = 1;
		long f = 0;
		
		for(int i=2; i<=N; i++){
			f = f2 + f1;
			f1 = f2;
			f2 = f;
		}
		
		return f;
	}
	
	public static void test20(){
		int N = 5;
		long r = 1;
		for(int i=N; i>0; i--){
			r *= i;
		}
		
		System.out.println(Math.log(r));
	}
	
	public static void test21(){
		System.out.printf("%s:%.3f,%.3f", "张三", 10.0, 20.0);
		System.out.println();
		System.out.printf("%s:%.3f,%.3f", "李四", 10.0, 20.0);
		System.out.println();
		System.out.printf("%s:%.3f,%.3f", "王五", 10.0, 20.0);
	}

	public static double binomial(int N, int k, int p){
		if(N==0 && k == 0){
			return 1.0;
		}
		if(N < 0 || k < 0){
			return 0.0;
		}
		
		return (1.0 - p)*binomial(N-1, k, p) + p*binomial(N-1, k-1, p);
	}
	
	public static void test30(){
		boolean[][] a = new boolean[10][10];
		
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a[i].length; j++){
				if((i == 0) || (j == 0)){
					a[i][j] = false;
					continue;
				}
				if((i%j == 0) || (j%i == 0)){
					a[i][j] = false;
				}else{
					a[i][j] = true;
				}
			}
		}
		
		for(int i=0; i<a.length; i++){
			System.out.println(Arrays.toString(a[i]));
		}
	}
}
