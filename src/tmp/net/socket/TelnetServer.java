package tmp.net.socket;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TelnetServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(1000);
			while(true){
				final Socket client = ss.accept();
				new Thread(){
					public void run(){
						echo(client);
					}
				}.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void echo(Socket client){
		try {
			writeString(client, "hello");
			while(true){
				String s = readString(client);
				System.out.println(client.getRemoteSocketAddress() + " : " + s);
				writeString(client, s);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	private static String readString(Socket client) throws IOException{
		InputStream is = client.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int data = 0;
		while((data = is.read()) != 13){
			if(data == -1){
				throw new EOFException("客户端连接断开");
			}
			baos.write(data);
		}
		is.read();
		return new String(baos.toByteArray(), "GBK");
	}
	private static void writeString(Socket client, String s) throws IOException{
		OutputStream os = client.getOutputStream();
		os.write(s.getBytes("GBK"));
		os.flush();
	}

}
