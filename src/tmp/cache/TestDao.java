package tmp.cache;

public interface TestDao {

	StorageKey key = StorageKey.TEST1;
	
	public String get(int roleId, String value);
	
	public void set(int roleId, String value);
	
	public void delete(int roleId);
	
}
