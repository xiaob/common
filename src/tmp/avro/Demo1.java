package tmp.avro;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import tmp.avro.stub.SimpleService;

public class Demo1 {

	public static void main(String[] args) throws IOException{
		Server server = getHttpServer(SimpleService.class, new ServiceImpl(), 1000);
		server.start();

//		server.close();
	}
	
	public static Server getNettyServer(Class<?> iface, Object impl, int port){
		return new NettyServer(new SpecificResponder(iface, impl), new InetSocketAddress(port));
	}
	
	public static Server getHttpServer(Class<?> iface, Object impl, int port) throws IOException{
		return new HttpServer(new SpecificResponder(iface, impl), port);
	}

}
