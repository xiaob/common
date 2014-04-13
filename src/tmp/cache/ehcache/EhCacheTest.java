package tmp.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheTest {

	public static final int DAY = 24*60*60;
	private CacheManager cacheManager;
	private Cache cache;
	
	public EhCacheTest(){
		cacheManager = CacheManager.newInstance("resource/ehcache.xml");
		cache = cacheManager.getCache("mainCache");
	}
	
	public void set(Object key, Object value, int expire){
		cache.put(new Element(key, value, expire, expire));
	}
	
	public <T> T get(Object key, Class<T> clazz){
		Element element = cache.get(key);
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
	
	public static void main(String[] args) {
		EhCacheTest ehCacheTest = new EhCacheTest();
		
		ehCacheTest.set("test1", "value1", 2*DAY);
		
		System.out.println(ehCacheTest.get("test1", String.class));
		
		ehCacheTest.shutdown();
	}

}
