package tmp.net.xsocket.udp;


public class UDPTest{
	
	public static void main(String args[])throws Exception{
		UDP udp = new UDP(3, "127.0.0.1", 8000, new BaseHandler());
		
		udp.start();
		udp.join();
	}
	
}

