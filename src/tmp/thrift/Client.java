package tmp.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import tmp.thrift.gen.SecondService;

public class Client {

	public static void main(String[] args)throws Exception {
		// 设置调用的服务地址为本地，端口为 7911 
        TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 1000)); 
        transport.open(); 
        // 设置传输协议为 TBinaryProtocol 
        TProtocol protocol = new TBinaryProtocol(transport); 
        SecondService.Client client = new SecondService.Client(protocol); 
        
        client.blahBlah(); 
        System.out.println(client.testString("1111"));
        
        transport.close(); 

	}

}
