package com.yuan.common.udp;

import java.io.IOException;
import java.net.DatagramPacket;

public class M1 {

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
	}
	
	public static void test2(){
		ServerDiscovery discovery = new ServerDiscovery("AGENT", "127.0.0.1", 1000);
		discovery.discover(new ServerListener(){
			@Override
			public void onServerJoin(RemoteServer remoteServer) {
				String remoteServerId = remoteServer.getServerId();
				if(remoteServerId.equals("WORLD")){
					System.out.println("receive shake from " + remoteServer.getServerIp() + ":" + remoteServer.getServerPort());
					remoteServer.shake();
				}else if(remoteServerId.equals("GAME")){
					remoteServer.shake();
				}
			}
		});
		discovery.shake();
	}

}
