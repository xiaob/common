package tmp.rmi;

import com.yuan.common.rmi.RMIProxyFactory;

public class Client2 {

	public static void main(String[] args) {
		try {
			User user = RMIProxyFactory.getProxy(User.class, "localhost", 8888);
			System.out.println(user.say("11111"));
			System.out.println(user.say("11111"));
			System.out.println(user.say("11111"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
