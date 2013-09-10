package tmp.rmi;

public class UserImpl implements User {

	@Override
	public String say(String msg) {
		System.out.println("=== " + msg);
		System.out.println("=== " + Thread.currentThread());
		return "qqqqqqqqq";
	}

}
