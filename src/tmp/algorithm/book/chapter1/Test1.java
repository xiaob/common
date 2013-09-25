package tmp.algorithm.book.chapter1;

public class Test1 {

	public static void main(String[] args) {
		test1();

	}
	
	// 浮点数字面量
	public static void test1(){
		double f1 = 10e15;
		System.out.println(f1);
		
		double f2 = 10e-15;
		System.out.println(f2);
	}

	// 矩阵相乘
	public static void test2(){
		int[][] a = new int[3][2];
		int[][] b = new int[2][3];
		
		initArray(a);
		initArray(b);
		
		for(int i=0; i<a.length; i++){
			int sum = 0;
			for(int j=0; j<a[i].length; j++){
				for(int k=0; k<b.length; k++){
					sum += a[i][j] * b[k][i];
				}
			}
//			System.out.println(x);
		}
	}
	private static void initArray(int[][] x){
		for(int i=0; i<x.length; i++){
			for(int j=0; j<x[i].length; j++){
				x[i][j] = 1;
			}
		}
	}
	
}
