package tmp.net.netty;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.DownstreamMessageEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyTcpServer {

	public static void main(String[] args) {
		// Configure the server.
//        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor(), 2));

        bootstrap.getPipeline().addLast("echoHandle", new EchoServerHandler());

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(8080));

	}

}

class EchoServerHandler extends SimpleChannelHandler{
	
	private static final Logger log = LoggerFactory.getLogger(EchoServerHandler.class);
	
	@Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel channel = e.getChannel();
		log.info(channel.getRemoteAddress().getClass().getName());
		log.info("建立连接：" + channel.getId() + ", " + channel.getRemoteAddress() + ". " + Thread.currentThread());
    }
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e){
		Channel channel = e.getChannel();
		log.info("断开连接：" + channel.getId() + ", " + channel.getRemoteAddress() + ". " + Thread.currentThread());
		channel.close();
	}
     
	
	@Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)throws Exception {
		log.info(e.getRemoteAddress().toString() + " : " + e.getMessage() + ". " + Thread.currentThread());
        // Send back the received message to the remote peer.
		ChannelBuffer buf = (ChannelBuffer)e.getMessage();
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		try {
			log.info(new String(data, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        e.getChannel().write(e.getMessage()).await();
        e.getChannel().close();
        ctx.sendDownstream(new DownstreamMessageEvent(e.getChannel(), null, null, e.getRemoteAddress()));
//        super.messageReceived(ctx, e)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        log.info("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
}