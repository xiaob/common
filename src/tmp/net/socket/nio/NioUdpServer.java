package tmp.net.socket.nio;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class NioUdpServer {

	public static void main(String[] args) {
		try {
			Selector selector = Selector.open();
			DatagramChannel channel = DatagramChannel.open();
			channel.configureBlocking(false);
			DatagramSocket socket = channel.socket();
			socket.bind(new InetSocketAddress(1000));
			channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
			
			NioUdpHandler handler = new TestNioUdpHandler();
			while(true){
				int n = selector.select();
		    	if(n == 0){
		    		continue;
		    	}
		    	Set<SelectionKey> readyKeys = selector.selectedKeys();
		    	for(SelectionKey key : readyKeys){
		    		readyKeys.remove(key);
		    		
		    		if(key.isReadable()){
		    			handler.handleRead(key);
		    		}//if
		    		if(key.isValid() && key.isWritable()){
		    			handler.handleWrite(key);
		    		}
		    	}
			}//while
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class TestNioUdpHandler implements NioUdpHandler{

	private final int PACKAGE_SIZE = 10;
	
	@Override
	public void handleRead(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer)key.attachment();
		DatagramChannel dc = (DatagramChannel)key.channel();
		InetSocketAddress client = (InetSocketAddress)dc.receive(buf); //接收来自任意一个Client的数据报
		key.interestOps(key.interestOps() | SelectionKey.OP_READ);
		
		System.out.println("client ----> IP: " + client.getAddress().getHostAddress() + ", port: " + client.getPort());
		System.out.println("buf.position() = " + buf.position());
        if(buf.position() >= PACKAGE_SIZE){
        	buf.flip();
        	DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buf.array()));
			System.out.println(dis.readInt());
			BufferedReader d = new BufferedReader(new InputStreamReader(dis));
			System.out.println(d.readLine());
			
            buf.clear();
        }else{
        	dc.register(key.selector(), SelectionKey.OP_READ, buf);
        }
		
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer)key.attachment();
		DatagramChannel dc = (DatagramChannel)key.channel();
		
		while(buf.hasRemaining()){
			int len = dc.write(buf);
			if(len < 0){
				throw new EOFException();
			}
			/*
			 * 在网络不好的时候,将此频道的OP_WRITE操作注册到Selector上,这样,当网络恢复,
			 * 频道可以继续将结果数据返回客户端的时候,Selector会通过SelectionKey来通知应用程序,
			 * 再去执行写的操作。这样就能节约大量的CPU资源,使得服务器能适应各种恶劣的网络环境
			 */
			if(len == 0){
				key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
				break;
			}
		}
	}
	
}
