package tmp.jetty;

import java.io.IOException;

import org.mortbay.jetty.Server;
import org.mortbay.xml.XmlConfiguration;
import org.xml.sax.SAXException;

public class JettyTest {

	public static void main(String[] args) throws SAXException, IOException, Exception {
		// Apply configuration to an existing object
        Server server = new Server();
        
        String serverConfig = "jetty.xml";
        new XmlConfiguration(serverConfig).configure(server);
        
        // configuration creates new object
//        configuration = new XmlConfiguration(context_config); 
//        ContextHandler context = (ContextHandler)configuration.configure();
//        
//        server.setHandler(context);
        server.start();

	}

}
