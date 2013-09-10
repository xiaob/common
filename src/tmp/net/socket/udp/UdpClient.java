package tmp.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;

public class UdpClient {

	public static void main(String[] args) {
		DatagramSocket ds = null;	
		try {
			ds = new DatagramSocket();
			
			writeString(ds, "hello world!", 512, "127.0.0.1", 1000);
			
			String s = readString(ds, 512);
			System.out.println(s);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ds != null){
				ds.close();
			}
		}

	}
	
	public static void writeString(DatagramSocket ds, String message, int size, String ip, int port) throws SocketException, IOException{
		byte[] buf = Arrays.copyOf(message.getBytes(), size);
		
		ds.send(new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, port)));
	}
	
	public static String readString(DatagramSocket ds, int size) throws IOException{
		byte[] buf = new byte[size];
		
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		ds.receive(dp);
		
		return new String(buf).trim();
	}
	
}
