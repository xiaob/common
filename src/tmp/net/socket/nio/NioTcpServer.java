package tmp.net.socket.nio;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioTcpServer {

	public static void main(String[] args) {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// 将其设为non-blocking状态，这样才能进行异步IO操作
			serverSocketChannel.configureBlocking(false);	
			serverSocketChannel.socket().setReuseAddress(true);
		    serverSocketChannel.socket().bind(new InetSocketAddress(1000));
		    
		    // 将ServerSocketChannel注册到Selector上，返回对应的SelectionKey
		    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //ServerSocketChannelImpl
		    
		    NioTcpHandler handler = new TestNioTcpHandler(1024);
		    while(true){
		    	int n = selector.select();
		    	if(n == 0){
		    		continue;
		    	}
		    	Set<SelectionKey> readyKeys = selector.selectedKeys();
		    	for(SelectionKey key : readyKeys){
		    		readyKeys.remove(key);
		    		if(key.isAcceptable()){
		    			handler.handleAccept(key);
		    		}
		    		if(key.isReadable()){
		    			handler.handleRead(key);
		    		}//if
					if(key.isValid() && key.isWritable()){
						handler.handleWrite(key);
					}
		    	}
		    }
		    
		    //关闭一个已经注册的SelectableChannel需要两个步骤
			//1. 取消注册的key，这个可以通过SelectionKey.cancel方法，也可以通过SelectableChannel.close方法，或者中断阻塞在该channel上的IO操作的线程来做到
			//2. 后续的Selector.selectXXX方法的调用才真正地关闭本地Socket
			//如果在取消SelectionKey后没有调用到selector的select方法, 那么本地socket将进入CLOSE-WAIT状态（等待本地Socket关闭）.
//		    serverSocketChannel.close();
//		    selector.selectNow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class TestNioTcpHandler implements NioTcpHandler{

	private int bufSize;
	private final int PACKAGE_SIZE = 10;
	
	public TestNioTcpHandler(int bufSize) {
		super();
		this.bufSize = bufSize;
	}

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
		SocketChannel sc = ssc.accept();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
		key.interestOps(key.interestOps() | SelectionKey.OP_ACCEPT);
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer)key.attachment();
		SocketChannel clientChannel = (SocketChannel)key.channel();
		Socket client = clientChannel.socket();
        clientChannel.read(buf);
        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        
        System.out.println("client ----> IP: " + client.getInetAddress().getHostAddress() + ", port: " + client.getPort());
        if(buf.position() >= PACKAGE_SIZE){
        	buf.flip();
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buf.array()));
			System.out.println(dis.readInt());
			BufferedReader d = new BufferedReader(new InputStreamReader(dis));
			System.out.println(d.readLine());
			
			key.cancel();
            key.channel().close();
            
            buf.clear();
        }else{
        	clientChannel.register(key.selector(), SelectionKey.OP_READ, buf);
        }
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer)key.attachment();
		buf.flip();
		SocketChannel clientChannel = (SocketChannel)key.channel();
		
		while(buf.hasRemaining()){
			int len = clientChannel.write(buf);
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
