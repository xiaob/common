package tmp.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 七大经典排序算法
 * 
 * 排序分为四种： 
      交换排序： 包括冒泡排序，快速排序。
      选择排序： 包括直接选择排序，堆排序。
      插入排序： 包括直接插入排序，希尔排序。
      合并排序： 合并排序。
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class Sort {

	public static void main(String[] args) {
		int[] a = random(20000);
		System.out.println(Arrays.toString(a));
		
		long start = System.currentTimeMillis();
		quickSort(a);
		long end = System.currentTimeMillis();
		
		System.out.println("耗时：" + (end - start) + "ms, " + Arrays.toString(a));
	}
	
	private static int[] random(int size){
		Random r = new Random();
		
		int[] a = new int[size];
		for(int i=0; i<size; i++){
			a[i] = r.nextInt(10000);
		}
		
		return a;
	}
	
	// 快速排序
	public static void quickSort(int[] a){
		quickSort(a, 0, a.length - 1);
	}
	private static void quickSort(int[] a, int beginIndex, int endIndex){
		if(beginIndex >= endIndex){
			return ;
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
		
		// 以left位置为分割点, 递归快排子数组
		if((left - 1) > beginIndex){
			quickSort(a, beginIndex, left - 1);
		}
		if((left + 1) < endIndex){
			quickSort(a, left + 1, endIndex);
		}
	}
	
	// 选择排序
	public static void selectSort(int[] a){
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a.length; j++){
				if(a[i] > a[j]){
					swap(a, i, j);
				}
			}
		}
	}
	private static void swap(int[] a, int i, int j){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

}
