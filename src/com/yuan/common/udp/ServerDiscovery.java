package com.yuan.common.udp;

import java.io.IOException;
import java.net.DatagramPacket;

public class ServerDiscovery {

	private static final int SIZE = 50;
	private Multicast multicast;

	private String serverId;
	private String localServerIp;
	private int localServerPort;

	public ServerDiscovery(String serverId, String localServerIp, int localServerPort) {
		this.serverId = serverId;
		this.localServerIp = localServerIp;
		this.localServerPort = localServerPort;

		try {
			// TODO 从配置文件加载
			String multicastIp = "228.5.6.7";
			int multicastPort = 6789;

			multicast = new Multicast(multicastIp, multicastPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shake() {
		if (multicast != null) {
			PacketBuffer packetBuffer = new PacketBuffer(100);
			packetBuffer.writeString(serverId);
			packetBuffer.writeString(localServerIp);
			packetBuffer.writeInt(localServerPort);
			try {
				multicast.send(packetBuffer.build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void discover(final ServerListener serverListener) {
		if (multicast == null) {
			throw new IllegalStateException("init failed! multicast is null");
		}

		final ServerDiscovery owner = this;
		multicast.startReceive(new DatagramPacketReceiver() {

			@Override
			public DatagramPacket newDatagramPacket() {
				return new DatagramPacket(new byte[SIZE], SIZE);
			}

			@Override
			public void onReceive(DatagramPacket datagramPacket) throws Exception {
				PacketBuffer packetBuffer = new PacketBuffer(datagramPacket.getData());
				String remoteServerId = packetBuffer.readString();
				String remoteServerIp = packetBuffer.readString();
				int remoteServerPort = packetBuffer.readInt();

				serverListener
						.onServerJoin(new RemoteServer(remoteServerId, remoteServerIp, remoteServerPort, owner));
			}

		});
	}
	
	public void shutdown(){
		if(multicast != null){
			multicast.shutdown();
		}
	}
}
