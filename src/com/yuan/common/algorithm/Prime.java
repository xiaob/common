package com.yuan.common.algorithm;

/**
 * 关于素数的两个算法
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class Prime {

	public static void main(String[] args) {
		System.out.println(sumPrime(1000));
		System.out.println(Integer.toBinaryString(-128));
	}
	
	/**
	 * 筛法求N以内素数之和
	 * @param N
	 * @return
	 */
	public static long sumPrime(int N){
		long sum = 0;
		int[] numbers = new int[N + 1];
		
		for(int i=0; i<=N; i++){
			numbers[i] = i;
		}
		numbers[0] = numbers[1] = 0; //筛掉0,1
		
		for(int j=2; j<=N; j++){
			if(isPrimeNumber(numbers[j])){//素数
				for(int k=2*j; k<=N; k+=j){
					numbers[k] = 0; //筛掉j的倍数
				}
			}
		}
		
		for(int m=0; m<=N; m++){
			sum += numbers[m];
		}
		return sum;
		
	}
	/**
	 * 是否素数
	 * 
	 * 只需要判断从2到根号N是否能除尽即可
	 * @param number
	 * @return
	 */
	public static boolean isPrimeNumber(int number){
		if(number < 2){
			return false;
		}
		
		int k = (int)Math.sqrt(number);
		for(int i=2; i<=k; i++){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}

}
