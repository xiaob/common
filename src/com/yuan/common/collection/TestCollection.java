package com.yuan.common.collection;


public class TestCollection {

	public static void main(String[] args) {
		testConcurrentMap();
	}
	
	public static void testMap(){
		MultiHashMap<Integer, Integer, String> map = new MultiHashMap<Integer, Integer, String>();
		map.put(1, 1, "1111");
		map.put(1, 2, "1122");
		map.put(1, 3, "1133");
		map.put(2, 1, "2211");
		System.out.println(map.get(1, 1));
		System.out.println(map.get(1, 2));
		System.out.println(map.get(1, 3));
		System.out.println(map.get(2, 1));
	}
	
	public static void testConcurrentMap() {
		LRUCache cache = new LRUCache(2);
		
		cache.set("1", 1);  
		cache.set("2", 2);  
		cache.set("3", 3);  
		System.out.println(cache.get("1", Integer.class));//null 已经失效了  
		System.out.println(cache.get("2", Integer.class)); 
	}

}
