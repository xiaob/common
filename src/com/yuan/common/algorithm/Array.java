package com.yuan.common.algorithm;

import java.util.Arrays;

/**
 * 关于数组的算法
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class Array {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,6,7,8};
		leftMove(a, 3);
		System.out.println(Arrays.toString(a));
	}
	
	/**
	 * 数组元素左移M位
	 * 
	 * 要实现在线性的时间内实现数组的快速移动，就要考虑如何使用逆序算法来达到移动的目的。
	 * 例如，我要移动的数组元素称为A，剩余的部分称为B，那么原来次序为AB，如何变成BA呢？
	 * 其实根据倒置的算法是可以实现移位操作的，我们先取A'为A的逆序序列，B'为B的逆序序列,
	 * 进行(A'B')'操作即可得到BA序列
	 * @param a
	 * @param m
	 */
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
	
}
