package tmp.net.xsocket.udp;

import java.util.concurrent.TimeUnit;

public class UDPTest2 {
	public static void main(String args[])throws Exception{
		UDP udp2 = new UDP(100, "127.0.0.1", 8001, new BaseHandler());
		for(int i=0;i<3;i++){
			udp2.send("127.0.0.1", 1000, "中");
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
}
