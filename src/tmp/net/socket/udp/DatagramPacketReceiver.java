package tmp.net.socket.udp;

import java.net.DatagramPacket;

public interface DatagramPacketReceiver {

	public DatagramPacket newDatagramPacket();
	
	public void onReceive(DatagramPacket datagramPacket)throws Exception;
	
}
