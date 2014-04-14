package tmp.cache.lrucache;

import tmp.cache.ICache;
import tmp.cache.StorageKey;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

public class LRUCache implements ICache{

	private ConcurrentLinkedHashMap<String, Object> map;
	
	public LRUCache(long capacity) {
		map = new ConcurrentLinkedHashMap.Builder<String, Object>().maximumWeightedCapacity(capacity)
				.weigher(Weighers.singleton()).build();
	}
	
	@Override
	public void set(StorageKey key, long cacheSuffix, Object value) {
		map.put(key.getName() + "_" + cacheSuffix, value);
	}

	@Override
	public <T> T get(StorageKey key, long cacheSuffix, Class<T> clazz) {
		Object value = map.get(key.getName() + "_" + cacheSuffix);
		
		if(value == null){
			return null;
		}
		
		return clazz.cast(value);
	}
}
