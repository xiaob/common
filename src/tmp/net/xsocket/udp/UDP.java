package tmp.net.xsocket.udp;

import java.io.IOException;
import java.net.InetAddress;

import org.xsocket.datagram.Endpoint;
import org.xsocket.datagram.IDatagramHandler;
import org.xsocket.datagram.IEndpoint;
import org.xsocket.datagram.UserDatagram;

//UPD最大报文长度64K
public class UDP implements Runnable{
	private IEndpoint endpoint;
	private int packetSize;
	private Thread t;
	
	public UDP(int packetSize, String host, int port, IDatagramHandler datagramHandler)throws IOException{
		endpoint = new Endpoint(packetSize, datagramHandler,  InetAddress.getByName(host), port);
		this.packetSize = packetSize;
		System.out.println("服务器启动 " + endpoint.getLocalPort()+"... ...");
	}
	
	public void send(String remoteHost, int remotePort, String data)throws IOException{
		UserDatagram request = new UserDatagram(remoteHost, remotePort, packetSize);
		request.write(data);
		endpoint.send(request);
	}
	
	public void send(String remoteHost, int remotePort, byte[] data)throws IOException{
		UserDatagram request = new UserDatagram(remoteHost, remotePort, packetSize);
		request.write(data);
		endpoint.send(request);
	}
	
	public void start(){
		if(t == null){
			t = new Thread(this);
		}
		t.start();
	}
	
	public void shutdown()throws IOException{
		if(endpoint != null){
			endpoint.close();
		}
		
		synchronized(this){
			this.notifyAll();
		}
	}
	
	public void join()throws InterruptedException{
		t.join();
	}
	
	public void run(){
		synchronized(this){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
