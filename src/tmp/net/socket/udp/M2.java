package tmp.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;

public class M2 {

	public static void main(String[] args) throws IOException {
		test2();
	}
	
	public static void test1() throws IOException{
		Multicast multicast = new Multicast("228.5.6.7", 6789);
		
		multicast.startReceive(new DatagramPacketReceiver(){

			@Override
			public DatagramPacket newDatagramPacket() {
				return new DatagramPacket(new byte[100], 100);
			}

			@Override
			public void onReceive(DatagramPacket datagramPacket) throws Exception {
				PacketBuffer packetBuilder = new PacketBuffer(datagramPacket.getData());
				System.out.println(packetBuilder.readString());
			}
			
		});
		
		PacketBuffer packetBuilder = new PacketBuffer(100);
		packetBuilder.writeString("222");
		multicast.send(packetBuilder.build());
	}
	
	public static void test2(){
		ServerDiscovery discovery = new ServerDiscovery(ServerType.WORLD, "127.0.0.1", 1001);
		discovery.discover(new ServerListener(){
			@Override
			public void onServerJoin(RemoteServer remoteServer) {
				switch (remoteServer.getServerType()) {
				case AGENT:
					System.out.println("connect to " + remoteServer.getServerIp() + ":" + remoteServer.getServerPort());
					break;
				case GAME:
					remoteServer.shake();
					break;
				default :
					break;
				}
			}
		});
		discovery.shake();
	}
	
}
