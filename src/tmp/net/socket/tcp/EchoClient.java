package tmp.net.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) {
		Socket socket = new Socket();
		try {
			socket = new Socket("127.0.0.1", 1000);
			printInfo(socket);
			
			writeString(socket, "hello world!");
			System.out.println(readString(socket));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	private static void writeString(Socket socket, String message) throws IOException{
		byte[] data = message.getBytes();
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeInt(data.length);
		dos.write(data);
		dos.flush();
	}
	
	private static String readString(Socket socket) throws IOException{
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		int length = dis.readInt();
		byte[] data = new byte[length];
		dis.read(data);
		return new String(data);
	}
	
	private static void printInfo(Socket socket){
		InetSocketAddress remoteAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		System.out.println(remoteAddress.getAddress().getHostAddress() + " : " + remoteAddress.getPort());
		
		InetSocketAddress localAddress = (InetSocketAddress)socket.getLocalSocketAddress();
		System.out.println(localAddress.getAddress().getHostAddress() + " : " + localAddress.getPort());
	}

}
