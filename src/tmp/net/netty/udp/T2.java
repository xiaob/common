package tmp.net.netty.udp;


public class T2 {

	public static void main(String[] args) {
		NettyUdp udp = new NettyUdp(1001, 100);
		for(int i=0; i<5; i++){
			udp.writeString("中国", "127.0.0.1", 1000);
		}
//		udp.shutdown();
	}

}
