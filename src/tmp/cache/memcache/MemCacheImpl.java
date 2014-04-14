package tmp.cache.memcache;

import tmp.cache.ICache;
import tmp.cache.StorageKey;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemCacheImpl implements ICache {

	private MemCachedClient cachedClient;
	
	public MemCacheImpl(String server){
		cachedClient = new MemCachedClient();
		SockIOPool pool = SockIOPool.getInstance();
		String[] servers = {server};
		pool.setServers(servers);
		pool.initialize();
	}
	
	@Override
	public void set(StorageKey key, long cacheSuffix, Object value) {
		cachedClient.set(key.getName() + "_" + cacheSuffix, value, key.getExpire());
	}

	@Override
	public <T> T get(StorageKey key, long cacheSuffix, Class<T> clazz) {
		Object value = cachedClient.get(key.getName() + "_" + cacheSuffix);
		if(value == null){
			return null;
		}
		
		return clazz.cast(value);
	}

}
