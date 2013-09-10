package tmp.net.netty;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyTcpClient {

	private static final Logger log = LoggerFactory.getLogger(NettyTcpClient.class);
			
	private ClientBootstrap bootstrap;
	private ChannelFuture future;
	
	public NettyTcpClient(){
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
	}
	
	public void addChannelHandler(String name, ChannelHandler handler){
		bootstrap.getPipeline().addLast(name, handler);
	}
	
	public void connect(String host, int port){
		future = bootstrap.connect(new InetSocketAddress(host, port));
	}
	
	public void write(ChannelBuffer buf) throws InterruptedException{
		final Object lock = new Object();
		future.getChannel().write(buf).addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture cf)throws Exception {
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		});
		synchronized (lock) {
			lock.wait();
		}
	}
	
	public void writeString(String message){
		try {
			byte[] data = message.getBytes("UTF-8");
			ChannelBuffer firstMessage = ChannelBuffers.buffer(data.length);
			firstMessage.writeBytes(data);
			log.info("1111111111111");
//			write(firstMessage);
			future.getChannel().write(firstMessage).await();
			log.info("222222222222");
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}
	
	public void close(){
		log.info("3333333");
		if(future != null){
			future.getChannel().close();
		}
		log.info("4444444");
		if(bootstrap != null){
			bootstrap.releaseExternalResources();
		}
	}
	
	public static void main(String[] args) {
		NettyTcpClient client = new NettyTcpClient();
		client.addChannelHandler("echo", new EchoClientHandler());
		client.connect("127.0.0.1", 8080);
		client.writeString("hello");
		client.close();
		
		
	}

}

class EchoClientHandler extends SimpleChannelHandler{
	
	private static final Logger log = LoggerFactory.getLogger(EchoClientHandler.class);
	
	@Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel channel = e.getChannel();
		log.info(channel.getRemoteAddress().getClass().getName());
		log.info("建立连接：" + channel.getId() + ", " + channel.getRemoteAddress());
    }
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e){
		Channel channel = e.getChannel();
		log.info("断开连接：" + channel.getId() + ", " + channel.getRemoteAddress());
		channel.close();
	}

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    	System.out.println("==============" + e.getMessage().toString());
    	ChannelBuffer buf = (ChannelBuffer) e.getMessage();   
    	byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		try {
			log.info(new String(data, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		e.getChannel().close(); 
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        log.info("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
    
}
