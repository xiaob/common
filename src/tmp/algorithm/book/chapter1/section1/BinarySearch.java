package tmp.algorithm.book.chapter1.section1;

import java.util.Arrays;

/**
 * 二分搜索
 *
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] whitelist = {1,2,3,4,5,6,7,8,9};
		Arrays.sort(whitelist);
		
		System.out.println(rank(4, whitelist));
	}
	
	// a是已经排过序的数组
	public static int rank(int key, int[] a){
		int lo = 0;
		int hi = a.length - 1;
		
		while(lo <= hi){
			int mid = lo + hi / 2;
			if(key > a[mid]){
				lo = mid + 1;
			}else if(key < a[mid]){
				hi = mid - 1;
			}else{
				return mid;
			}
		}
		// 没有找到
		return -1;
	}

}
