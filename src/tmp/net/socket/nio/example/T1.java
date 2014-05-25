package tmp.net.socket.nio.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class T1 {

	public static void main(String[] args) throws Exception {

		SelectorThread selectorThread = new SelectorThread();
		AcceptThread acceptThread = new AcceptThread(selectorThread);
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		acceptThread.regist(serverSocketChannel);
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.bind(new InetSocketAddress(9000));
		
		acceptThread.start();
		selectorThread.start();
	}
	
}

class AcceptThread extends AbstractNioThread{
	private SelectorThread selectorThread;
	
	public AcceptThread(SelectorThread selectorThread) throws IOException{
		super("AcceptThread");
		this.selectorThread = selectorThread;
	}
	
	public void regist(SelectableChannel channel) throws Exception{
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	@Override
	protected void handle(SelectionKey key) throws Exception {
		if(key.isAcceptable()){
			SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
			selectorThread.regist(socketChannel);
			System.out.println(socketChannel);
		}
	}
}
class SelectorThread extends AbstractNioThread{
	
	private BlockingQueue<SelectableChannel> queue = new LinkedBlockingQueue<SelectableChannel>();
	
	public SelectorThread() throws IOException {
		super("SelectorThread");
	}

	public void regist(SelectableChannel channel) throws Exception{
		queue.put(channel);
		selector.wakeup();
	}
	
	@Override
	protected void handle(SelectionKey key) throws Exception {
		if(key.isReadable()){
			SocketChannel socketChannel = (SocketChannel)key.channel();
			ByteBuffer buf = (ByteBuffer)key.attachment();
			
			int count = socketChannel.read(buf);
			System.out.println(count);
		}
	}
	
	protected void handleFinished()throws Exception{
		while(!stopping.get()){
			SelectableChannel channel = queue.poll();
			if(channel == null){
				break;
			}
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(10));
		}
	}

}

