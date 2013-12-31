package tmp.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Search {

	public static void main(String[] args) {
		int[] a = random(10);
		Arrays.sort(a);
		
		long start = System.currentTimeMillis();
		System.out.println(binarySearch(a, a[3]));
		long end = System.currentTimeMillis();
		
		System.out.println("耗时：" + (end - start) + "ms, ");
		
		System.out.println(hash("111"));
		System.out.println(hash("222"));
		
		System.out.println(Arrays.toString(a));
		// 查找中位数
		System.out.println(quickSearch(a, a.length/2));
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
	
	// 查找第K小的数
	public static int quickSearch(int[] a, int k){
		if((k < 0) || (k > a.length)){
			return -1;
		}
		
		return quickSearch(a, 0, a.length - 1, k);
	}
	private static int quickSearch(int[] a, int beginIndex, int endIndex, int k){
		if(beginIndex > endIndex){
			return -1;
		}
		
		int left = beginIndex;
		int right = endIndex;
		int base = a[left];
		
		// 直到left指针与right指针重合, 一轮排序才结束
		while(left < right){
			// 向前查找直到找到比base小的数, 然后赋值给left位置
			while(left < right){
				if(a[right] >= base){
					right --;
				}else{
					a[left] = a[right];
					break;
				}
			}
			// 向后查找直到找到比base大的数, 然后赋值给right位置
			while(left < right){
				if(a[left] <= base){
					left ++;
				}else{
					a[right] = a[left];
					break;
				}
			}
		}
		// 最后将base插入到left位置
		a[left] = base;
		
		// 以left位置为分割点, 递归查找子数组
		if((k-1+beginIndex) < left){
			if((left - 1) >= beginIndex){
				return quickSearch(a, beginIndex, left - 1, k);
			}
			return -1;
		}else if((k-1+beginIndex) > left){
			if((left + 1) <= endIndex){
				return quickSearch(a, left + 1, endIndex, (k-1+beginIndex) - left);
			}
			return -1;
		}else{
			return a[left]; // 刚好是中间点
		}
	}
	
}
