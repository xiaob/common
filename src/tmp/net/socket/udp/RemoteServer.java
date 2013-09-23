package tmp.net.socket.udp;

public class RemoteServer {

	private ServerType serverType;
	private String serverIp;
	private int serverPort;
	private ServerDiscovery serverDiscovery;
	
	public RemoteServer(ServerType serverType, String serverIp, int serverPort, ServerDiscovery serverDiscovery) {
		super();
		this.serverType = serverType;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.serverDiscovery = serverDiscovery;
	}
	
	public void shake(){
		serverDiscovery.shake();
	}

	public ServerType getServerType() {
		return serverType;
	}

	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}
	
}
