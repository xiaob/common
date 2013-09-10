package tmp.net.xsocket;

import java.io.IOException;
import java.nio.BufferUnderflowException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import com.yuan.common.util.SystemTool;

public class XClient {
	
	private static final Logger log = LoggerFactory.getLogger(XClient.class);
	
	public static void main(String[] args)throws Exception {
		test1();
	}
	
	public static void test()throws Exception{
		IBlockingConnection conn = new BlockingConnection("127.0.0.1", 9000);
		int w = conn.write("11111");
		System.out.println("w = " + w);
		byte[] r = conn.readBytesByLength(3);
		System.out.println("r = " + r);
		for(byte b : r)
		System.out.println(b);
		conn.close();
	}
	
	public static void test1()throws Exception{
		INonBlockingConnection nbc = new NonBlockingConnection("127.0.0.1", 9000, new DefaultDataHandler());
		IBlockingConnection conn = new BlockingConnection(nbc);
		conn.write("111111");
		nbc.setHandler(new DefaultDataHandler());
		nbc.write("Hello" + Http.SEPARATOR);
		log.info("客户端开始 ... ");
		String cmd = "";
		while(!(cmd = SystemTool.readFromConsole()).equals("bye")){
			nbc.write(cmd + Http.SEPARATOR);
			nbc.flush();
		}
		nbc.close();
	}
	
	public static void test2()throws Exception{
		HttpClient2 httpClient = new HttpClient2();
		log.info("test2客户端开始 ... ");
		String cmd = "";
		while(!(cmd = SystemTool.readFromConsole()).equals("bye")){
			if(cmd.equals("")){
				continue;
			}
			HttpResponse2 response = httpClient.doGet(cmd);
			log.info(response.toString());
		}
	}
	
}

class DefaultDataHandler implements IDataHandler {
	public boolean onData(INonBlockingConnection nbc) throws IOException,
			BufferUnderflowException, MaxReadSizeExceededException {

		// read the whole logical part or throwing a BufferUnderflowException
		String res = nbc.readStringByDelimiter("\r\n");
		System.out.println(res);

		return true;
	}
}
