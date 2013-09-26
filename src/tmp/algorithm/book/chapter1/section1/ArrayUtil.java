package tmp.algorithm.book.chapter1.section1;

import java.util.Arrays;

// 矩阵的乘法
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
}
