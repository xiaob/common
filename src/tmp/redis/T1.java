package tmp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class T1 {

	public static void main(String[] args) {
		test1();
	}
	
	public static void test1(){
		JedisPoolConfig config = new JedisPoolConfig();  
//	    config.setMaxActive(Integer.valueOf(bundle  
//	            .getString("redis.pool.maxActive")));  
//	    config.setMaxIdle(Integer.valueOf(bundle  
//	            .getString("redis.pool.maxIdle")));  
//	    config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));  
//	    config.setTestOnBorrow(Boolean.valueOf(bundle  
//	            .getString("redis.pool.testOnBorrow")));  
//	    config.setTestOnReturn(Boolean.valueOf(bundle  
//	            .getString("redis.pool.testOnReturn"))); 
		JedisPool pool = new JedisPool(config, "192.168.56.102", 6379);
				
	    Jedis jedis = new Jedis("192.168.56.102");  
//	    jedis = pool.getResource();
	    String keys = "name";  
	      
	    // 删数据  
	    jedis.del(keys);  
	    // 存数据  
	    jedis.set(keys, "snowolf");  
	    // 取数据  
	    String value = jedis.get(keys);  
	      
	    System.out.println(value);  
	    
	    jedis.close();
	    
	    // try{}finaly{}
//	    pool.returnResource(jedis);
//	    pool.destroy();
	}

}
