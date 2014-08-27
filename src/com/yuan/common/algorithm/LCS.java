package com.yuan.common.algorithm;

public class LCS {

	public static void main(String[] args) {

		System.out.println(lcs("akqrshrengxqiyxuloqk", "tdzbujtlqhecaqgwfzbc"));
	}
	
	/**
	 * 最长公共子序列
	 * @param x
	 * @param y
	 * @return
	 */
	public static String lcs(String x, String y){
		int len1 = x.length();
		int len2 = y.length();
		
		// 构造二维数组记录子问题x[i]和y[i]的LCS的长度
		int[][] opt = new int[len1 + 1][len2 + 1];

		// 动态规划计算所有子问题
		for (int i = len1 - 1; i >= 0; i--) {
			for (int j = len2 - 1; j >= 0; j--) {
				if (x.charAt(i) == y.charAt(j))
					opt[i][j] = opt[i + 1][j + 1] + 1; // 参考上文我给的公式。
				else
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]); // 参考上文我给的公式。
			}
		}
		
		StringBuilder result = new StringBuilder();
		int i = 0, j = 0;
		while (i < len1 && j < len2) {
			if (x.charAt(i) == y.charAt(j)) {
				result.append(x.charAt(i));
				i++;
				j++;
			} else if (opt[i + 1][j] >= opt[i][j + 1])
				i++;
			else
				j++;
		}
		
		return result.toString();
	}
}
