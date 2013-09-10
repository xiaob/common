package tmp.ws;

import java.net.UnknownHostException;

public class WSServer {

	public static void main(String[] args) {
		WSEndpoint.addService(new Hello());
		WSEndpoint.addService("Test", new Hello());
		
		try {
			WSEndpoint.publish();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
