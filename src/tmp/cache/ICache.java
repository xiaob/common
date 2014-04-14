package tmp.cache;

public interface ICache {

	public void set(StorageKey key, long cacheSuffix, Object value);
	
	public <T extends Object> T get(StorageKey key, long cacheSuffix, Class<T> clazz);
	
}
