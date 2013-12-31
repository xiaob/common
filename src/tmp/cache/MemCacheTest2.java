package tmp.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemCacheTest2 {

	public static void main(String[] args) throws IOException, MemcachedException, InterruptedException, TimeoutException {
		MemcachedClient memcachedClient = getCache("192.168.56.101:11211");
		memcachedClient.add("name", 20000, "zhang");
		Object obj = memcachedClient.get("name", new SerializingTranscoder());
		System.out.println("==========" + obj);
//		obj.setRole(3);
//		memcachedClient.set("4151888", 0, obj);
//		Map<InetSocketAddress, Map<String, String>> map = memcachedClient.getStats();
//		for(Map.Entry<InetSocketAddress, Map<String, String>> entry : map.entrySet()){
//			print(entry.getKey(), entry.getValue());
//		}
		
		memcachedClient.shutdown();
	}
	
	private static void print(InetSocketAddress address, Map<String, String> map){
		System.out.println("============ " + address.getAddress().toString() + ":" + address.getPort());
		for(Map.Entry<String, String> entry : map.entrySet()){
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}
	
	public static MemcachedClient getCache(String address) throws IOException{
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address));
		//默认使用的TextCommandFactory，也就是文本协议。
		builder.setCommandFactory(new BinaryCommandFactory());//use binary protocol 

		MemcachedClient memcachedClient = builder.build();

		return memcachedClient;
	}

}
