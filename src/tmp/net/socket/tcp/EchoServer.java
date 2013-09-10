package tmp.net.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		try {
			serverSocket = new ServerSocket(1000);
			System.out.println(serverSocket.getInetAddress().getHostAddress() + " : " + serverSocket.getLocalPort());
			while(true){
				final Socket connection = serverSocket.accept();
				executorService.submit(new Runnable() {
					public void run() {
						echo(connection);
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			executorService.shutdownNow();
			if(serverSocket != null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void echo(Socket connention){
		printInfo(connention);
		try {
			String message = readString(connention);
			System.out.println(message);
			writeString(connention, message);
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			try {
				connention.close();
			} catch (IOException e) {
				e.printStackTrace();
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
