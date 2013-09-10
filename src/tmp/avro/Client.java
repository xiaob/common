package tmp.avro;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import tmp.avro.stub.Message;
import tmp.avro.stub.SimpleService;

public class Client {

	public static void main(String[] args) throws IOException {
		Transceiver transceiver = getHttpTransceiver(new URL("http", "127.0.0.1", 1000, "/"));
		
		SimpleService proxy = SpecificRequestor.getClient(SimpleService.class, transceiver);
		
//		Message message = new Message();
//		message.setTopic("2222");
//		message.setContent(ByteBuffer.wrap(new byte[0]));
//		message.setCreatedTime(0L);
//		message.setIds("wwwwwwww");
//		message.setIpAddress("ip");
//		message.setProps(new HashMap<CharSequence, CharSequence>());
		List<Message> list = new ArrayList<Message>();
//		list.add(message);
		System.out.println(proxy.publish("11111", list));
		
		transceiver.close();
	}
	
	public static Transceiver getNettyTransceiver(String host, int port) throws IOException{
		return new NettyTransceiver(new InetSocketAddress(host, port));
	}
	
	public static Transceiver getHttpTransceiver(URL url){
		return new HttpTransceiver(url);
	}

}
