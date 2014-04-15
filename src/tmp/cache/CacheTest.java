package tmp.cache;

import tmp.cache.ehcache.EhCacheImpl;
import tmp.cache.lrucache.LRUCache;
import tmp.cache.memcache.MemCacheImpl;

public class CacheTest {

	public static void main(String[] args) {
		ICache cache1 = new EhCacheImpl();
		ICache cache2 = new LRUCache(100000);
		ICache cache3 = new MemCacheImpl("192.168.56.101:11211");
		
		test(cache2);
		
//		cache.shutdown();
	}
	
	public static void test(ICache cache){
        cache.set(StorageKey.TEST1, 0, "value1");
		
		System.out.println(cache.get(StorageKey.TEST1, 0, String.class));
	}

}
