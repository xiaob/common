package tmp.net.xsocket.udp;

import java.io.IOException;

import org.xsocket.datagram.IDatagramHandler;
import org.xsocket.datagram.IEndpoint;
import org.xsocket.datagram.UserDatagram;

public class BaseHandler implements IDatagramHandler {
	
    public boolean onDatagram(IEndpoint localEndpoint) throws IOException {
         UserDatagram datagram = localEndpoint.receive();  // get the datagram
         if(datagram == null){
        	 return false;
         }
         String remoteHost = datagram.getRemoteAddress().getHostAddress();
         int remotePort = datagram.getRemotePort();
         System.out.println("[" + remoteHost + ":" + remotePort + "] : " + datagram.readString().trim());
         
//        UserDatagram request = new UserDatagram(remoteHost, remotePort, datagram.getSize());
// 		request.write("回复");
// 		localEndpoint.send(request);
        return true;
    }
    
 }
