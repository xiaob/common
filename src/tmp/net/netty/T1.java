package tmp.net.netty;


public class T1 {

	public static void main(String[] args) throws Exception {
		NettyUdp server = new NettyUdp(100);
		server.start(1000);

	}

}
