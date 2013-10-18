package com.yuan.common.udp;

public class RemoteServer {

	private String serverId;
	private String serverIp;
	private int serverPort;
	private ServerDiscovery serverDiscovery;
	
	public RemoteServer(String serverId, String serverIp, int serverPort, ServerDiscovery serverDiscovery) {
		super();
		this.serverId = serverId;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.serverDiscovery = serverDiscovery;
	}
	
	public void shake(){
		serverDiscovery.shake();
	}

	public String getServerId() {
		return serverId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}
	
}
