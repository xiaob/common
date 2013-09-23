package tmp.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Multicast {

	private MulticastSocket socket;
	private InetAddress group;
	private int port;
	private MulticastReceiver multicastReceiver;
	
	public Multicast(String ip, int port) throws IOException{
		this.port = port;
		group = InetAddress.getByName(ip);
		socket = new MulticastSocket(port);
		socket.joinGroup(group);
	}
	
	public void startReceive(DatagramPacketReceiver datagramPacketReceiver){
		if(multicastReceiver == null){
			multicastReceiver = new MulticastReceiver(socket, datagramPacketReceiver);
			multicastReceiver.start();
		}
	}
	
	public void send(DatagramPacket datagramPacket) throws IOException{
		datagramPacket.setAddress(group);
		datagramPacket.setPort(port);
		socket.send(datagramPacket);
	}
	
	public void shutdown() {
		if(socket != null){
			try {
				socket.leaveGroup(group);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(multicastReceiver != null){
				multicastReceiver.shutdown();
			}
			
			socket.close();
		}
	}
}

class MulticastReceiver extends Thread{
	
	private MulticastSocket socket;
	private DatagramPacketReceiver datagramPacketReceiver;
	private AtomicBoolean stopping = new AtomicBoolean(false);
	
	public MulticastReceiver(MulticastSocket socket, DatagramPacketReceiver datagramPacketReceiver) {
		super("MulticastReceiver");
		this.socket = socket;
		this.datagramPacketReceiver = datagramPacketReceiver;
	}
	
	public void run(){
		while(!stopping.get()){
			try {
				DatagramPacket datagramPacket = datagramPacketReceiver.newDatagramPacket(); 
				socket.receive(datagramPacket);  
				datagramPacketReceiver.onReceive(datagramPacket);
			} catch (Exception e) {
				e.printStackTrace();
			}   
        } 
	}
	
	public void shutdown(){
		stopping.set(true);
		
		interrupt();
	}
	
}
