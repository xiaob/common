package tmp.net.netty.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;

/**
 * 但对 UDP 的开发可与 TCP 相差较大。首先，UDP 是无连接的，因此，必须自己在应用层考虑并实现数据传输的可靠性， 比如顺序、丢包检测、重发等等。在
 * Netty 中，无论客户端还是服务端，都必须采用 ConnectionlessBootstrap。 然后 ChannelFactory 可采用
 * NioDatagramChannelFactory。服务端使用bind绑定地址端口开始监听，客户端直接 connect，
 * 后面就不细说了。在消息获取事件回调方法中，你通过 MessageEvent 参数对象（而不是 ChannelHandlerContext）的
 * getChannel() 方法获取当前会话连接，但是其 isConnected() 永远都返回 false，所以这里没有必要沿用 TCP 开发中在调用
 * channel 的 write 发送网络消息前检查 channel 是否已连接。UDP 开发中在消息获取事件回调方法中，获取了当前会话连接 channel
 * 对象后可直接通过 channel 的 write 方法发送数据给对端，但这里必须使用 write 的两个参数的版本形态（Netty API
 * 文档中对此有专门说明），第一个参数仍然是要发送的消息对象，第二个参数则是要发送的对端 SocketAddress 地址对象。 但是这里要特别注意，不能通过
 * channel.getRemoteAddress() 获取对端地址对象，因为 channel 总是未连接状态，所以无法获取对端地址对象。只能通过
 * MessageEvent 对象参数的 getRemoteAddress() 方法获取即可。
 * 
 * @author yuan<cihang.yuan@happyelements.com>
 * 
 */
public class NettyUdp {

	private int size;
	private ConnectionlessBootstrap b;
	private DatagramChannel datagramChannel;

	public NettyUdp(int port, int size) {
		super();
		this.size = size;
		
		init(port);
	}
	
	private void init(int port){
		b = new ConnectionlessBootstrap(new NioDatagramChannelFactory(Executors.newCachedThreadPool()));

		b.setOption("tcpNoDelay", true);
		b.setOption("receiveBufferSize", 1048576); // 1M
		 
		b.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("stick", new FixedLengthFrameDecoder(size));
				pipeline.addLast("decoder", new UDPDecoder(size));
				pipeline.addLast("encoder", new UDPEncoder(size));
				pipeline.addLast("logic", new MyHandler());

				return pipeline;
			}
		});
		
		datagramChannel = (DatagramChannel) b.bind(new InetSocketAddress(port));
		System.out.println(" Server is starting ……");
	}
	
	public void writeString(String message, String remoteHost, int remotePort) {
		datagramChannel.write(message, new InetSocketAddress(remoteHost, remotePort));
	}

	public static void main(String[] args) throws IOException {
		new NettyUdp(1000, 100);
		
	}
	
	public void shutdown(){
		if(datagramChannel != null){
			datagramChannel.close();
		}
		if(b != null){
			b.releaseExternalResources();
		}
	}

}

class MyHandler extends SimpleChannelUpstreamHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println(e.getRemoteAddress() + " ->：" + e.getMessage());
		
//		e.getChannel().write(message, remoteAddress)
	}

}
