package tmp.algorithm.book.chapter1.section1;

import java.util.Arrays;

// 牛顿迭代法计算平方根
// 调和级数
public class ArrayUtil {

	public static void main(String[] args) {
		double[] a = {1.0,2.0,3.0,4.0,5.0};
		System.out.println(max(a));
		System.out.println(average(a));
		double[] b = copy(a);
		System.out.println(b + ", " + Arrays.toString(b));
		reserve(a);
		System.out.println(Arrays.toString(a));
		
		double[][] a1 = {{1,1}, {2,0}};
		double[][] b1 = {{0,2,3}, {1,1,2}};
		double[][] c = multiply(a1, b1);
		System.out.println(Arrays.toString(c[0]));
		System.out.println(Arrays.toString(c[1]));
		
		sqrt(100);
		System.out.println(hypotenuse(10, 10));
		System.out.println(H(100));
	}
	
	// 找出数组中的最大元素
	public static double max(double[] a){
		double max = a[0];
		for(int i=1; i<a.length; i++){
			if(a[i] > max){
				max = a[i]; 
			}
		}
		return max;
	}
	
	// 计算数组元素的平均值
	public static double average(double[] a){
		int N = a.length;
		double sum = 0.0;
		for(int i=0; i<N; i++){
			sum += a[i];
		}
		return sum / N;
	}
	
	// 复制数组
	public static double[] copy(double[] a){
		int N = a.length;
		double[] b = new double[N];
		for(int i=0; i<N; i++){
			b[i] = a[i];
		}
		return b;
	}
	
	// 数组元素逆序
	public static void reserve(double[] a){
		int N = a.length;
		for(int i=0; i<N/2; i++){
			double tmp = a[i];
			a[i] = a[N -1 - i];
			a[N - 1 - i] = tmp;
		}
	}
	
	/**
	 * 矩阵相乘
	 * 只有当矩阵A的列数与矩阵B的行数相等时A×B才有意义。
	 * 一个m×n的矩阵a（m,n)左乘一个n×p的矩阵b（n,p)，会得到一个m×p的矩阵c（m,p)
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[][] multiply(double[][] a, double[][] b){
		int m = a.length;
		int p = b[0].length;
		int n = a[0].length;
		if(n > b.length){
			n = b.length;
		}
		double[][] c = new double[m][p];
		
		for(int i=0; i<m; i++){
			for(int j =0; j<p; j++){
				for(int k=0 ;k<n; k++){
					c[i][j] += a[i][k]*b[k][j];
				}
			}
		}
		
		return c;
	}
	
	public static int abs(int x){
		if(x < 0) 
			return -x;
		else
			return x;
	}
	
	public static double abs(double x){
		if(x < 0.0){
			return -x;
		}else{
			return x;
		}
	}
	
	/**
	 * 牛顿迭代法求平方根
	 * @param n
	 * @return
	 */
	public static double sqrt(double n){
		if(n < 0){
			return Double.NaN;
		}
		
		double err = 1e-15;
		double xn = n;
		while(Math.abs(xn - n/xn) > err*xn){
			xn = (xn + n/xn)/2.0;
			System.out.println(xn);
		}
		
		return xn;
	}
	
	/**
	 * 计算直角三角形的斜边
	 * @param a
	 * @param b
	 * @return
	 */
	public static double hypotenuse(double a, double b){
		
		return Math.sqrt(a*a  + b*b);
	}
	
	// 计算调和级数
	public static double H(int N){
		double sum = 0.0;

		for(int i=1; i<=N; i++){
			sum += 1.0/i;
		}
		
		return sum;
	}
}
