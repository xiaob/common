package tmp.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JmxAgent {

	private MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
	private String domain;
	
	public JmxAgent(String domain){
		this.domain = domain;
	}
	
	public JmxAgent(String domain, MBeanServer mBeanServer) {
		super();
		this.domain = domain;
		this.mBeanServer = mBeanServer;
	}

	public void registMBean(String id, Object mBean)throws Exception{
		ObjectName objectName = new ObjectName(domain + ":type=" + id);
		mBeanServer.registerMBean(mBean, objectName);
	}
	
	public void registMBeanGroup(String group, Map<String, Object> mBeanMap)throws Exception{
		for(String id : mBeanMap.keySet()){
			ObjectName objectName = new ObjectName(domain + ":type=" + group + ",name=" + id);
			mBeanServer.registerMBean(mBeanMap.get(id), objectName);
		}
	}
	
	public Object getAttribute(String id, String attribute)throws Exception{
		ObjectName objectName = new ObjectName(domain + ":type=" + id);
		return mBeanServer.getAttribute(objectName, attribute);
	}
	
	public void remotePublish(int port)throws IOException, MalformedURLException{
		LocateRegistry.createRegistry(port);
		remotePublish("rmi", null, port, null);
	}
	
	public void remotePublish(String protocol, String host, int port, String urlPath)throws IOException, MalformedURLException{
		 JMXServiceURL url = new JMXServiceURL(protocol, host, port, urlPath);   
		 System.out.println(url);
	     JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);   
	     cs.start();   
	}
	
	public void remotePublish(String host, int port)throws IOException, MalformedURLException{
//		String url = "service:jmx:rmi://localhost:1098/jndi/rmi://"+host+":"+port+"/jmxrmi";
		 String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
		 LocateRegistry.createRegistry(port);
		 JMXServiceURL serviceurl = new JMXServiceURL(url);   
		 System.out.println(serviceurl);
	     JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(serviceurl, null, mBeanServer);   
	     cs.start();  
	}
	
	public static void main(String args[])throws Exception{
		ServerImpl serverImpl = new ServerImpl();

		JmxAgent jmx = new JmxAgent("农政通");
		jmx.registMBean("公文流转", new ServerMonitor(serverImpl));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test1", new ServerMonitor(serverImpl));
		jmx.registMBeanGroup("测试子分组", map);
//		jmx.remotePublish("192.168.1.102", 2000);
		
		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}

}
