package com.yuan.common.math;

public class MathUtil {

	/**
	 * 检查两个整数的乘积是否溢出
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static boolean checkMultiplyOutOfInteger(int i1, int i2){
		int p1 = Math.abs(i1);
		int p2 = Math.abs(i2);
		
		return (Integer.MAX_VALUE / p1) < p2;
	}
	
}
