package tmp.rmi;

import com.yuan.common.rmi.RMIEndpoint;


public class Server2 {

	public static void main(String[] args) {
		RMIEndpoint.addService(User.class, new UserImpl());
		try {
			RMIEndpoint.publish(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
