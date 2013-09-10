package tmp.net.xsocket;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.concurrent.TimeUnit;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

public class XSocketTcpClient {

	public static void main(String[] args) {
		try {
			IBlockingConnection bc = new BlockingConnection("127.0.0.1", 1000);
			bc.write(100);
			bc.write("你好");
			
			TimeUnit.SECONDS.sleep(100);
//			bc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void test()throws Exception{
		// defining the client handler (here as a anonymous inner class)
		IDataHandler clientHandler = new IDataHandler() {
		 
		    public boolean onData(INonBlockingConnection nbc) 
		             throws IOException, 
		             BufferUnderflowException, 
		             MaxReadSizeExceededException {
		 
		      // read the whole logical part or throwing a BufferUnderflowException
		      String res = nbc.readStringByDelimiter("\r\n");
		      //...

		      return true;
		   }
		};
		 
		// opening the connection
		INonBlockingConnection nbc = new NonBlockingConnection("127.0.0.1", 1000, clientHandler);
		nbc.write("Hello");
		 
		// do something else. Receiving data causes that the client 
		// handler's onData method will be called (within a dedicated thread)
		//...
	}

}
