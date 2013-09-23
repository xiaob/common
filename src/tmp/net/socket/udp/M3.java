package tmp.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;

public class M3 {

	public static void main(String[] args) throws IOException {
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
		packetBuilder.writeString("333");
		multicast.send(packetBuilder.build());

	}
	
}
