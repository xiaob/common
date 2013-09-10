package com.yuan.common.math;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.stat.StatUtils;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

public class TestMath {

	public static void main(String[] args) {
		test2();
	}
	
	public static void test1() {
		double[] values = new double[] { 0.33, 1.33, 0.27333, 0.3, 0.501,
				0.444, 0.44, 0.34496, 0.33, 0.3, 0.292, 0.667 };
		System.out.println("最小值: " + StatUtils.min(values));
		System.out.println("最大值: " + StatUtils.max(values));
		System.out.println("算术平均值: " + StatUtils.mean(values)); // Returns
		System.out.println("平方和: " + StatUtils.sumSq(values)); // Returns
		System.out.println("标准方差: " + new StandardDeviation().evaluate(values));
	}
	
	public static void test2(){
		RandomData randomData = new RandomDataImpl(); 
        for (int i = 0; i < 10; i++){
            long value = randomData.nextLong(1, 100);
            System.out.println(value);
        }

        System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        
        for (int i = 0; i < 10; i++){
            randomData = new RandomDataImpl(); 
            long value = randomData.nextLong(1, 100);
            System.out.println(value);
        }
	}
	/**
	 * 方程求解.
	 * 2x + 3y - 2z = 1
	 * -x + 7y + 6x = -2
	 * 4x - 3y - 5z = 1
	 */
	public static void test3(){
		double[][] coefficientsData = {{2, 3, -2}, {-1, 7, 6}, {4, -3, -5}};
        RealMatrix coefficients = new Array2DRowRealMatrix(coefficientsData, false);
        DecompositionSolver solver = new LUDecompositionImpl(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(new double[] { 1, -2, 1 }, false);
        RealVector solutionVector = solver.solve(constants);
        double[] solution = solutionVector.getData();
        System.out.println(solution[0]);
        System.out.println(solution[1]);
        System.out.println(solution[2]);
	}

}
