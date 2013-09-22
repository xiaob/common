package tmp.net.netty.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.oio.OioDatagramChannelFactory;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;

public class Multicast {
	private int size;
	private ConnectionlessBootstrap b;
	private DatagramChannel datagramChannel;

	public Multicast(int port, int size) {
		super();
		this.size = size;
		
		init(port);
	}
	
	private void init(int port){
		b = new ConnectionlessBootstrap(new OioDatagramChannelFactory(Executors.newCachedThreadPool()));

		b.setOption("broadcast", true); // 多播模式
		b.setOption("interface", new InetSocketAddress("224.0.0.1", port)); // 多播数据包的网络接口地址
		 
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
