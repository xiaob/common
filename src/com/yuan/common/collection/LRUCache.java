package com.yuan.common.collection;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

public class LRUCache {

	private ConcurrentLinkedHashMap<String, Object> map;
	
	public LRUCache(long capacity) {
		map = new ConcurrentLinkedHashMap.Builder<String, Object>().maximumWeightedCapacity(capacity)
				.weigher(Weighers.singleton()).build();
	}
	
	public void set(String key, Object value){
		map.put(key, value);
	}
	
	public <T> T get(String key, Class<T> clazz){
		Object value = map.get(key);
		
		if(value == null){
			return null;
		}
		
		return clazz.cast(value);
	}
}
