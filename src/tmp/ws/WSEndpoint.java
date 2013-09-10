package tmp.ws;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.ws.Endpoint;

public class WSEndpoint {

	private static final ConcurrentMap<String, Object> serviceCache = new ConcurrentHashMap<String, Object>();
	
	public static void addService(Object service){
		addService(service.getClass().getSimpleName(), service);
	}
	public static void addService(String serviceName, Object service){
		serviceCache.put(serviceName, service);
	}
	
	public static void publish() throws UnknownHostException{
		publish(80);
	}
	public static void publish(int port) throws UnknownHostException{
		publish(InetAddress.getLocalHost().getHostAddress(), port);
	}
	public static void publish(String host){
		publish(host, 80);
	}
	public static void publish(String host, int port){
		String address = "http://" + host + ":" + port + "/";
		Set<Map.Entry<String, Object>> entries = serviceCache.entrySet();
		for(Map.Entry<String, Object> entry : entries){
			Endpoint.publish(address + entry.getKey(), entry.getValue());
		}
	}
	
}
