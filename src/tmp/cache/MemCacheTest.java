package tmp.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemCacheTest {

	public static void main(String[] args) {
		test2();
	}
	
	public static void test1(){
		MemCachedClient cachedClient = getCached("192.168.100.103:11211");
		
		cachedClient.add("name", "zhang");
		cachedClient.add("age", 22);
		System.out.println(cachedClient.get("age"));
	}
	
	public static void test2(){
//		MemCachedClient cachedClient = getCached("192.168.5.205:11211");
//		
//		CharInfo charInfo = (CharInfo)cachedClient.get("4151888");
//		System.out.println("====" + charInfo.getRole());
//		charInfo.setRole(3);
//		cachedClient.set("4151888", charInfo);
	}
	
	public static MemCachedClient getCached(String server){
		MemCachedClient cachedClient = new MemCachedClient();
		SockIOPool pool = SockIOPool.getInstance();
		String[] servers = {server};
		pool.setServers(servers);
		pool.initialize();
		
		return cachedClient;
	}

}
