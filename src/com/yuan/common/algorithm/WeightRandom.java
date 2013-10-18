package com.yuan.common.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 按权重生成随机数
 * @author yuan<cihang.yuan@happyelements.com>
 *
 * @param <T>
 */
public class WeightRandom<T> {
	
	/**
	 * key 是 weightList的索引
	 */
	private Map<Integer, T> map = new HashMap<Integer, T>();
	
	/**
	 * 权重总和
	 */
	private int weightSum = 0;
	
	/**
	 * 权重列表
	 */
	private List<Integer> weightList = new ArrayList<Integer>();
	private Random random = new Random();
	
	/**
	 * 添加权重
	 * @param weight
	 * @param data
	 */
	public void addWeight(int weight, T data){
		weightList.add(weight);
		map.put(weightList.size() - 1, data);
		weightSum += weight;
	}
	
	/**
	 * 按权重随机返回数据
	 * @return
	 */
	public T next(){
		int currentWeight = 0;
		int rd = random.nextInt(weightSum) + 1; // 产生随机数[1, weightSum]
		
		for(int i=0; i<weightList.size(); i++){
			currentWeight += weightList.get(i);
			if(currentWeight >= rd){
				return map.get(i);
			}
		}
		
		throw new RuntimeException("bad WeightRandom!!!");
	}
}
