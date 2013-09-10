package tmp.net.xsocket.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xsocket.datagram.Endpoint;
import org.xsocket.datagram.IDatagramHandler;
import org.xsocket.datagram.IEndpoint;
import org.xsocket.datagram.UserDatagram;

public class XSocketUdpServer {

	public static void main(String[] args) {
		try {
			IEndpoint endpoint = new Endpoint(10, new XsocketUdpServerHandler(),  InetAddress.getByName("127.0.0.1"), 1000);
			
			Object lock = new Object();
			synchronized (lock) {
				lock.wait();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class XsocketUdpServerHandler implements IDatagramHandler {
	
    public boolean onDatagram(IEndpoint localEndpoint) throws IOException {
         UserDatagram datagram = localEndpoint.receive();  // get the datagram
         if(datagram == null){
        	 return false;
         }
         System.out.println("client ----> IP : " + datagram.getRemoteAddress().getHostAddress() + ", port = " + datagram.getRemotePort());
         System.out.println("" + datagram.readInt());
         System.out.println("" + datagram.readString());
         
//        UserDatagram request = new UserDatagram(datagram.getRemoteAddress().getHostAddress(), datagram.getRemotePort(), datagram.getSize());
// 		request.write("哈");
// 		localEndpoint.send(request);
         return true;
    }
    
 }
