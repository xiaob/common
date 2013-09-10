package tmp.jmx;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JmxServer {

	public static void main(String[] args) throws IOException{
//		JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(""));
		Registry registry = LocateRegistry.getRegistry();
		for(String s : registry.list()){
			System.out.println(s);
		}
	}

}
