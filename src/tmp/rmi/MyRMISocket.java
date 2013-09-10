package tmp.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
/**
 * RMI除了注册端口外，其通信端口是服务器随机产生的，
 * 因此不容易穿过防火墙。解决方法，自定义一个RMISocketFactory，
 * 指定固定的通信端口，然后使用setSocketFactory(RMISocketFactory fac)设置
 * @author yuan
 *
 */
public class MyRMISocket extends RMISocketFactory {  
	
	private int myPort;
	
    public MyRMISocket(int myPort) {
		super();
		this.myPort = myPort;
	}

	public Socket createSocket(String host, int port)   
         throws IOException{  
         return new Socket(host,port);  
     }  
    
    public ServerSocket createServerSocket(int port)   
        throws IOException {  
        if (port == 0)  
            port = myPort;//不指定就随机  
        return new ServerSocket(port);  
    } 
    
}