package tmp.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Search {

	public static void main(String[] args) {
		int[] a = random(200);
		Arrays.sort(a);
		
		long start = System.currentTimeMillis();
		System.out.println(binarySearch(a, a[2]));
		long end = System.currentTimeMillis();
		
		System.out.println("耗时：" + (end - start) + "ms, ");
		
		System.out.println(hash("111"));
		System.out.println(hash("222"));
	}
	
	private static int[] random(int size){
		Random r = new Random();
		
		int[] a = new int[size];
		for(int i=0; i<size; i++){
			a[i] = r.nextInt(10000);
		}
		
		return a;
	}

	// 折半查找
	public static int binarySearch(int[] a, int key){
		return binarySearch(a, key, 0, a.length - 1);
	}
	private static int binarySearch(int[] a, int key, int low, int high){
		if(low > high){
			return -1;
		}
		
		int middle = (low + high) / 2;
		
		if(a[middle] > key){
			return binarySearch(a, key, low, middle - 1);
		}else if(a[middle] < key){
			return binarySearch(a, key, middle + 1, high);
		}else{
			return middle;
		}
	}
	
	public static long hash(String str){
		long hash = 0;
		
		for(int i=0; i<str.length(); i++){
			hash = 31*hash + str.charAt(i);
		}
		
		return hash;
	}
	
}
