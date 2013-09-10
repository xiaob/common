package tmp.net.xsocket;

import java.net.InetAddress;

public class TestIP {

	public static void main(String[] args)throws Exception {
		InetAddress address = InetAddress.getByName("www.wmis.com.cn");
		System.out.println(address.toString());

	}

}
