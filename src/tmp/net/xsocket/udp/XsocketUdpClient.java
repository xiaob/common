package tmp.net.xsocket.udp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.xsocket.datagram.Endpoint;
import org.xsocket.datagram.IEndpoint;
import org.xsocket.datagram.UserDatagram;

public class XsocketUdpClient {

	public static void main(String args[]){
		try {
			IEndpoint endpoint = new Endpoint();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(baos);
		    dos.writeInt(200);
		    dos.write("你好".getBytes());
		    System.out.println("字节数: " + dos.size());
		    byte[] buf = baos.toByteArray();
			UserDatagram ud = new UserDatagram(new InetSocketAddress("127.0.0.1", 1000), buf);
			
			endpoint.send(ud);
			endpoint.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
