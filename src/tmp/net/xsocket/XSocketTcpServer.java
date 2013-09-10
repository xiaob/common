package tmp.net.xsocket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

import com.yuan.common.util.DateUtil;

public class XSocketTcpServer {

	public static void main(String[] args) {
		try {
			IServer srv = new Server(1000, new ServerHandler());
			//设置当前的采用的异步模式   
//			srv.setFlushmode(FlushMode.ASYNC);

			//设置连接的超时时间和最大空闲时间
//			srv.setConnectionTimeoutMillis(5000);
			srv.setIdleTimeoutMillis(10000);
			
			srv.run();  // the call will not return
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ServerHandler implements IDataHandler,IConnectHandler,IDisconnectHandler,IIdleTimeoutHandler  { 
	
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
	
	public boolean onData(INonBlockingConnection nbc)throws IOException, BufferUnderflowException, MaxReadSizeExceededException { 
		log(nbc.readInt() + "");
		log(nbc.readStringByLength(6) + "");
		
//		connection.write("服务器返回 : " + cmd + STR_FLAG);
//		connection.flush();
		return true;
	}
	
	public boolean onIdleTimeout(INonBlockingConnection connection)throws IOException{
		System.out.println(DateUtil.format(new Date(), "yyyy年MM月dd日 E HH:mm:ss.SSS"));
		return true;

	}
}
