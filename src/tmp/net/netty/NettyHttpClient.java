package tmp.net.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpVersion;

public class NettyHttpClient {

	private ClientBootstrap bootstrap;
	private Channel channel;
	
	public static void main(String[] args) throws Exception{
		NettyHttpClient httpClient = new NettyHttpClient();
		httpClient.connent("www.baidu.com", 80);

		System.out.println(httpClient.doGet("/"));
	}
	
	public void connent(String hostname, int port){
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// 设置 pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory(){

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				// Create a default pipeline implementation.
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("codec", new HttpClientCodec());
				pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
				//http处理handler
				pipeline.addLast("handler", new ClientHandler(queue));
				return pipeline;
			}
			
		});
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(hostname, port)).awaitUninterruptibly();
		channel = future.getChannel();
	}
	
	private SynchronousQueue<String> queue = new SynchronousQueue<String>();
	
	public String doGet(String url) throws InterruptedException{
		DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url);
		
		return execute(request);
	}
	
	public String doPost(String url, byte[] data) throws InterruptedException{
		DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url);
		request.setContent(ChannelBuffers.copiedBuffer(data));
		
		return execute(request);
	}
	
	private String execute(HttpRequest request) throws InterruptedException{
		if(channel != null){
			channel.write(request).await();
			
			return queue.take();
		}
		
		return null;
	}
	
}

class ClientHandler extends SimpleChannelUpstreamHandler{
	
	private SynchronousQueue<String> queue;
	
	public ClientHandler(SynchronousQueue<String> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if(e.getMessage() instanceof HttpChunk){
			
		}else{
			HttpResponse response = (HttpResponse) e.getMessage();
			String result = response.getContent().toString(Charset.forName("UTF-8"));
			try {
				queue.put(result);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
