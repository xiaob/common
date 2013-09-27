package tmp.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import tmp.thrift.gen.SecondService;

public class Server {

	public static void main(String[] args) throws TTransportException {
		System.out.println("thrift server open port 1000");
		
	    TProcessor processor = new SecondService.Processor<Handler>(new Handler()); 
	    
	    startServer(1000, processor);
	}
	
	public static void startServer(int port, TProcessor processor) throws TTransportException{
		TNonblockingServerSocket socket = new TNonblockingServerSocket(port);  
		
		TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(socket);
	    serverArgs.protocolFactory(new TBinaryProtocol.Factory(true, true));
	    serverArgs.transportFactory(new TFramedTransport.Factory());
	    serverArgs.processor(processor);
	    
	    TServer server = new TThreadedSelectorServer(serverArgs);   
	    server.serve();
	    
//	    server.stop();
//	    socket.close();
	}

}

class Handler implements SecondService.Iface {

	@Override
	public void blahBlah() throws TException {
		System.out.println("blahBlah");
		
	}

	@Override
	public String testString(String thing) throws TException {
		System.out.println(thing);
		final SyncValue<String> syncValue = new SyncValue<String>();
		
		new Thread(){
			public void run(){
				try {
					TimeUnit.SECONDS.sleep(5);
					syncValue.put("testString");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		try {
			return syncValue.get(3, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}