package tmp.net.xsocket;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

public class Main {

	public static void main(String[] args) throws Exception{
		IServer srv = new Server(9000, new EchoHandler()); 
//		srv.setWorkerpool(Executors.newFixedThreadPool(2));
	    srv.start();
	    log("服务器开始监听端口9000 ... ...");
	}
	
	public static void log(String msg){
		System.out.println(msg);
	}

}

class EchoHandler implements IDataHandler,IConnectHandler,IDisconnectHandler  { 
	
	public static final String STR_FLAG = "\r\n";
	
	private Map<INonBlockingConnection, Date> nbcMap = new ConcurrentHashMap<INonBlockingConnection, Date>();
	
	public static void log(String msg){
		System.out.println(msg);
	}
	
	private void printOnLine(){
		log("在线列表: ");
		log("================================");
		for(INonBlockingConnection inbc : nbcMap.keySet()){
			log(inbc.getRemotePort() + ", " + nbcMap.get(inbc));
		}
		log("================================");
	}
	
	private void writeOnLine(INonBlockingConnection nbc)throws IOException{
		nbc.write("在线列表: " + STR_FLAG);
		nbc.write("================================" + STR_FLAG);
		for(INonBlockingConnection inbc : nbcMap.keySet()){
			nbc.write(inbc.getRemotePort() + ", " + nbcMap.get(inbc) + STR_FLAG);
		}
		nbc.write("================================" + STR_FLAG);
		nbc.flush();
	}
	
	public boolean onConnect(INonBlockingConnection nbc) throws IOException { 
		log("连接 : " + nbc.getRemoteAddress() + ", " + nbc.getRemotePort() + ", 编码 : " + nbc.getEncoding());
		nbcMap.put(nbc, new Date());
//		writeOnLine(nbc);
		printOnLine();
		return true;
	}
	
	public boolean onDisconnect(INonBlockingConnection nbc) throws IOException { 
		nbcMap.remove(nbc);
		log(nbc + "离开了.");
		printOnLine();
		return true;
	}
	
	public boolean onData(INonBlockingConnection connection)throws IOException, BufferUnderflowException, MaxReadSizeExceededException { 
		String cmd = connection.readStringByDelimiter("\n","utf8"); 
		log("信息 : " + cmd);
		
		connection.write("服务器返回 : " + cmd + STR_FLAG);
		connection.flush();
		return true;
	}
}
