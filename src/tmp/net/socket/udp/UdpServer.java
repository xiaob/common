package tmp.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer {

	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(1000);
//			ds.setSoTimeout(3 * 1000); //接收超时时间
			while(true){
				String s = readString(ds, 512);
				System.out.println(s);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ds != null){
				ds.close();
			}
		}

	}
	
	public static String readString(DatagramSocket ds, int size) throws IOException{
		byte[] buf = new byte[size];
		
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
//		Arrays.copyOfRange(dp.getData(), dp.getOffset(), dp.getOffset() + dp.getLength());
		
		ds.receive(dp);
		
		ds.send(dp);
		
		return new String(buf).trim();
	}

}
