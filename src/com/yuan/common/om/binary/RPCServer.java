package com.yuan.common.om.binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import javax.imageio.ImageIO;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.yuan.common.swing.ImageTool;

public class RPCServer {
	
	private Server server;
//	private final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
//	private List<ScreenClient> clientList = new ArrayList<ScreenClient>();
	
	public void start(){
		server = new Server();
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				InetSocketAddress client = c.getRemoteAddressTCP();
				System.out.println("client --> ip: " + client.getAddress().getHostAddress() + ", port: " + client.getPort());
				Student stu = (Student)object;
//				
				server.sendToTCP(c.getID(), new Student("tcp response: helloworld", 30));
//				server.sendToUDP(c.getID(), new Student("udp response: helloworld", 40));
//				
//				server.sendToTCP(c.getID(), new int[]{1,2});
				ByteArrayOutputStream boas = new ByteArrayOutputStream();
				try {
					ImageIO.write(ImageTool.captureScreen(), "jpg", boas);
				} catch (IOException e) {
					e.printStackTrace();
				}
//				byte[] data = boas.toByteArray();
//				for(int i=0; i<data.length; i+=1024){
//					int to = i + 1024;
//					if(to > data.length-1){
//						to = data.length-1;
//					}
//					server.sendToUDP(c.getID(), Arrays.copyOfRange(data, i, to));
//				}
				server.sendToTCP(c.getID(), boas.toByteArray());
				
				
				System.out.println(stu.getName());
				System.out.println(stu.getAge());
				
//				server.sendToUDP(c.getID(), ImageTool.captureScreen());
			}
			public void connected(Connection connection){
//				InetSocketAddress client = connection.getRemoteAddressUDP();
//				clientList.add(new ScreenClient(client.getAddress().getHostAddress(), client.getPort()));
			}
			public void disconnected(Connection connection){
				
			}
		});
		try {
			server.bind(1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
		
//		service.scheduleWithFixedDelay(new ScreenSender(server, clientList), 1, 2, TimeUnit.SECONDS);
		//server.stop();
	}
	public static void main(String[] args) {
		new RPCServer().start();
		
	}
	
}

class ScreenSender implements Runnable{
	
	private Server server;
	private List<ScreenClient> clientList;
	private Client client;
	
	public ScreenSender(Server server, List<ScreenClient> clientList){
		this.server = server;
		this.clientList = clientList;
		client = new Client();
		client.start();
		Network.register(client);
	}
	public void run(){
		for(ScreenClient screenClient : clientList){
			try {
				client.connect(5000, screenClient.getIp(), screenClient.getPort(), screenClient.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			try {
				ImageIO.write(ImageTool.captureScreen(), "jpg", boas);
			} catch (IOException e) {
				e.printStackTrace();
			}
			server.sendToUDP(client.getID(), boas.toByteArray());
		}
	}
}

class ScreenClient{
	private String ip;
	private int port;
	
	public ScreenClient(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
