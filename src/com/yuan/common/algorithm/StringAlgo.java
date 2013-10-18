package com.yuan.common.algorithm;

/**
 * 字串符相关的算法
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class StringAlgo {

	
	public static void main(String[] args) {
		String s1 = "wabcadfsf";
		String s2 = "acadfbfws";
		
		System.out.println(equalsString(s1, s2));

	}

	/**
	 * 字符串中每个个字符的数相等则返回两个字符串相等，
	 * 字符串里面字符的顺序没有要求
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsString(String s1, String s2){
		if(s1.length() != s2.length()){
			return false;
		}
		
		int result = 0;
		for(int i=0; i<s1.length(); i++){
			result ^= s1.charAt(i)^s2.charAt(i);
		}
		
		return result == 0;
	}
}
