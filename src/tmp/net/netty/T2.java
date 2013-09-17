package tmp.net.netty;

public class T2 {

	public static void main(String[] args) {
		NettyUdp udp = new NettyUdp(100);
		udp.connect("127.0.0.1", 1000);
		for(int i=0; i<5; i++){
			udp.writeString("中国");
		}
		udp.shutdown();
	}

}
