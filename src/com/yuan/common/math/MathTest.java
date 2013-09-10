package com.yuan.common.math;

public class MathTest {

	public static void main(String[] args) {
		test2();
	}
	
	//计算定积分
	public static void test1(){
		Integ integ = new Integ();
		double value = integ.getValueTrapezia(0.0, 1.0, 0.00001);
		System.out.println(value);
	}
	
	//矩阵运算
	public static void test2(){
		double[] mtxData1 = {
			 1,  3,  -2,  0,  4,
			-2, -1,   5, -7,  2,
			 0,  8,   4,  1, -5,
			 3, -3,   2, -4,  1
		};
		double[] mtxData2 = {
			 4,  2, -7,  0,  3,
			 9,  6,  1,  8, -2,
			-4,  7,  2, -5,  5,
			 9, -8,  3,  6,  5
		};
		double[] mtxData3 = {
			 4,  5, -1,
			 2, -2,  6,
			 7,  8,  1,
			 0,  3, -5,
			 9,  8, -6
		};
		double[] mtxData4 = {
				 1,  2,  3,  4,
				 5,  6,  7,  8,
				 9, 10, 11, 12,
				13, 14, 15, 16 
			};
		double num = 2.0;
		Matrix mtx1 = new Matrix(4, 5, mtxData1);
		Matrix mtx2 = new Matrix(4, 5, mtxData2);
		Matrix mtx3 = new Matrix(5, 3, mtxData3);
		Matrix mtx4 = new Matrix(4, 4, mtxData4);
		System.out.println(mtx1);
		System.out.println(mtx2);
		
		System.out.println("mtx1 + mtx2 = \n" + mtx1.add(mtx2)); //加法
		System.out.println("mtx1 - mtx2 = \n" + mtx1.subtract(mtx2)); //减法
		System.out.println("mtx1 * mtx2 = \n" + mtx1.multiply(mtx2)); //乘法
		System.out.println("mtx1 * num = \n" + mtx1.multiply(num)); //数乘
		System.out.println("mtx1 转置  = \n" + mtx1.transpose()); //转置
		
		//矩阵求逆,求行列式值, 要求行数和列数相同的N阶方阵
		System.out.println("mtx4 行列式值  = " + mtx4.computeDetGauss()); //求行列式值
		if(mtx4.invertGaussJordan()){
			System.out.println("mtx4 求逆  = \n" + mtx4); //求逆
		}
		
		//求秩,三角分解,QR分解 要求矩阵的行数大于或者等于列数
		System.out.println("mtx4 秩  = " + mtx3.computeRankGauss()); //求秩
		Matrix mtx5 = new Matrix(5, 3, mtxData3);
		Matrix mtxL = new Matrix();
		Matrix mtxU = new Matrix();
		if(mtx5.splitLU(mtxL, mtxU)){//三角分解
			System.out.println("L矩阵  = \n" + mtxL); 
			System.out.println("U矩阵  = \n" + mtxU); 
		}
		Matrix mtx6 = new Matrix(5, 3, mtxData3);
		Matrix mtxQ = new Matrix();
		if(mtx6.splitQR(mtxQ)){//QR分解
			System.out.println("Q矩阵  = \n" + mtxQ); 
			System.out.println("R矩阵  = \n" + mtx6); 
		}
		
		//任意实矩阵的奇异值分解
//		Matrix mtx7 = new Matrix(5, 3, mtxData3);
		Matrix mtx7 = new Matrix(4, 5, mtxData1);
		Matrix mtxV = new Matrix();
		Matrix mtxU2 = new Matrix();
		if(mtx7.splitUV(mtxU2, mtxV, 0.0001)){//奇异值分解
			System.out.println("V矩阵  = \n" + mtxV); 
			System.out.println("U矩阵  = \n" + mtxU2); 
			System.out.println("A矩阵  = \n" + mtx7); 
		}
	}

}

class Integ extends Integral{
	
	public double func(double x){
		return Math.exp(-x*x);
	}
}
