package tmp.algorithm;

public class TestArray {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,6,7,8};
		leftMove(a, 3);
		printArray(a);
	}
	
	//数组元素左移M位
	public static void leftMove(int[] a, int m){
		m %= a.length;
		
		reserve(a, 0, m);
		reserve(a, m, a.length);
		reserve(a, 0, a.length);
	}
	//数组逆序 O(N)
	private static void reserve(int[] a, int left, int right){
		for(int i=left,j=right-1; i<j; i++,j--){
			swap(a, i, j);
		}
	}
	private static void swap(int[] a, int i, int j){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	public static void printArray(int[] a){
		for(int i=0; i<a.length; i++){
			System.out.print(a[i] + ",");
		}
	}

}
