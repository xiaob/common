package tmp.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import tmp.cache.ICache;
import tmp.cache.StorageKey;

public class EhCacheImpl implements ICache {

	private CacheManager cacheManager;
	private Cache cache;
	
	public EhCacheImpl(){
		cacheManager = CacheManager.newInstance("resource/ehcache.xml");
		cache = cacheManager.getCache("mainCache");
	}
	
	@Override
	public void set(StorageKey key, long cacheSuffix, Object value) {
		
		cache.put(new Element(key.getName() + "_" + cacheSuffix, value, key.getExpire(), key.getExpire()));
	}

	@Override
	public <T> T get(StorageKey key, long cacheSuffix, Class<T> clazz) {
		Element element = cache.get(key.getName() + "_" + cacheSuffix);
		if(element == null){
			return null;
		}
		Object value = element.getObjectValue();
		if(value == null){
			return null;
		}
		return clazz.cast(value);
	}
	
	public void shutdown(){
		if(cacheManager != null){
			cacheManager.shutdown();
		}
	}

}
