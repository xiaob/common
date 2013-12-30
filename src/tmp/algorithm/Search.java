package tmp.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Search {

	public static void main(String[] args) {
		int[] a = random(200);
		Arrays.sort(a);
		
		long start = System.currentTimeMillis();
		System.out.println(binarySearch(a, a[13]));
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
		int low = 0;
		int high = a.length - 1;
		
		while(low <= high){
			int middle = (low + high) / 2;
			System.out.println("low=" + low + ", high=" + high + ", middle=" + middle);
			if(key > a[middle]){
				low = middle + 1;
				continue;
			}else if(key < a[middle]){
				high = middle - 1;
				continue;
			}else{
				return middle;
			}
		}
		
		return -1;
	}
	
	public static long hash(String str){
		long hash = 0;
		
		for(int i=0; i<str.length(); i++){
			hash = 31*hash + str.charAt(i);
		}
		
		return hash;
	}
	
}
