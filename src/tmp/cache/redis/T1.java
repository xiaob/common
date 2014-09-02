package tmp.cache.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

public class T1 {

	public static void main(String[] args) {
		testBasic();
	}
	
	public static void testBasic(){
		Jedis jedis = new Jedis("192.168.56.102"); 
		
		testJedis(jedis);
		
		jedis.close();
	}
	
	public static void testPool(){
		JedisPoolConfig config = new JedisPoolConfig();  
//		config.setMaxIdle(5);
//		config.setMaxWaitMillis(1000l);
//		config.setTestOnBorrow(false); 
//		
//		JedisPool pool = new JedisPool(config, "192.168.56.102", 6379);
//		Jedis jedis = pool.getResource();
//		
//		testJedis(jedis);
//		
//		pool.returnResource(jedis);
//		pool.destroy();
	}
	
	public static void testShard(){
//		JedisPoolConfig config = new JedisPoolConfig();  
//		config.setMaxIdle(5);
//		config.setMaxWaitMillis(1000l);
//		config.setTestOnBorrow(false); 
		
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.56.102", 6379, "master"));
		// 构造池
//		ShardedJedisPool pool = new ShardedJedisPool(config, shards);
//		ShardedJedis jedis = pool.getResource();
		
//		testJedis(jedis);
//		
//		pool.returnResource(jedis);
//		pool.destroy();
	}
	
	public static void testJedis(JedisCommands jedis){
		String keys = "name";  
	    // 删数据  
	    jedis.del(keys);  
	    // 存数据  
	    jedis.set(keys, "snowolf");  
	    // 取数据  
	    String value = jedis.get(keys);  
	    
	    //SETEX key seconds value将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。
	    jedis.setex("foo", 5, "haha");
//	    redis.flushAll();清空所有的key
	      
	    System.out.println(value);  
	}

}
