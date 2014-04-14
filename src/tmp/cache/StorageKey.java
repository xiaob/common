package tmp.cache;

public enum StorageKey {

	TEST1("test1", 2*24*60*60)
	;
	/** 存储名称 */
	private String name;
	/** 过期时间,单位秒 */
	private int expire;
	
	private StorageKey(String name, int expire) {
		this.name = name;
		this.expire = expire;
	}

	public String getName() {
		return name;
	}

	public int getExpire() {
		return expire;
	}
	
}
