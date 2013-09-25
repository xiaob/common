package tmp.algorithm.book.chapter1.section1;

// 矩阵的乘法
// 牛顿迭代法计算平方根
// 调和级数
public class ArrayUtil {

	public static void main(String[] args) {
		double[] a = {1.0,2.0,3.0};
		System.out.println(max(a));
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

}
