package tmp.net.netty;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.jboss.netty.handler.codec.http.multipart.MixedAttribute;
import org.jboss.netty.util.CharsetUtil;

import com.alibaba.fastjson.JSON;

public class NettyHttpServer {
	
	public static void main(String[] args) {
		start(80);

	}
	
	public static void start(int port) {
		// 配置服务器-使用java线程池作为解释线程
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// 设置 pipeline factory.
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		// 绑定端口
		bootstrap.bind(new InetSocketAddress(port));
		System.out.println("admin start on "+port);
	}

	private static class ServerPipelineFactory implements
			ChannelPipelineFactory {
		public ChannelPipeline getPipeline() throws Exception {
			// Create a default pipeline implementation.
			ChannelPipeline pipeline = Channels.pipeline();
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("encoder", new HttpResponseEncoder());
			//http处理handler
			pipeline.addLast("handler", new AdminServerHandler());
			return pipeline;
		}
	}

}

class AdminServerHandler extends SimpleChannelUpstreamHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();
		if(request.getMethod().equals(HttpMethod.POST)){
			doPost(e, request);
		}else if(request.getMethod().equals(HttpMethod.GET)){
			doGet(e, request);
		}
	}
	
	private void doPost(MessageEvent e, HttpRequest request)throws Exception{
		String uri = request.getUri();
		if(uri.matches("/service")){
			Map<String, String> params = parsePostRequestParams(request);
			System.out.println(params);
			writeObject(e, params);
		}else{
			writeObject(e, "welcome!!!");
		}
	}
	
	private void doGet(MessageEvent e, HttpRequest request)throws Exception{
		String uri = request.getUri();
		QueryStringDecoder decoder = new QueryStringDecoder(uri);
		String path = decoder.getPath();
		if(path.matches("/")){
			writeObject(e, "welcome!!!");
		}else if(path.matches("/service")){
			Map<String, String> params = parseGetRequestParams(request);
			System.out.println(params);
			params.put("server", "test");
			writeObject(e, params);
		}else{
			sendError(e, HttpResponseStatus.NOT_FOUND);
		}
	}
	
	private Map<String, String> parseGetRequestParams(HttpRequest request) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		
		QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
		Map<String, List<String>> paramsMap = decoder.getParameters();

		for (String name : paramsMap.keySet()) {
			List<String> list = paramsMap.get(name);
			if((list != null) && (!list.isEmpty())){
				map.put(name, list.get(0));
			}
		}
		return map;
	}
	private Map<String, String> parsePostRequestParams(HttpRequest request) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		
		HttpPostRequestDecoder postdeDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(), request, CharsetUtil.UTF_8);
		List<InterfaceHttpData> dataList = postdeDecoder.getBodyHttpDatas();
		
		for (InterfaceHttpData data : dataList) {
			String name = data.getName();
			if (InterfaceHttpData.HttpDataType.Attribute == data.getHttpDataType()) {
				MixedAttribute attribute = (MixedAttribute) data;
				attribute.setCharset(CharsetUtil.UTF_8);
				String value = attribute.getValue();
				map.put(name, value);
			}
		}
		return map;
	}
	
	private void writeObject(MessageEvent e, Object obj){
		writeJSON(e, JSON.toJSONString(obj));
	}
	private void writeJSON(MessageEvent e, String json) {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

		response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=UTF-8");
		response.setContent(ChannelBuffers.copiedBuffer(json, CharsetUtil.UTF_8));
		// write the response
		ChannelFuture future = e.getChannel().write(response);
		// close connection after the write operation is done
		future.addListener(ChannelFutureListener.CLOSE);
	}
	
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
//			throws Exception {
//		Channel ch = e.getChannel();
//		Throwable cause = e.getCause();
//		if (cause instanceof TooLongFrameException) {
//			sendError(ctx, BAD_REQUEST);
//			return;
//		}
//
//		cause.printStackTrace();
//		if (ch.isConnected()) {
//			sendError(ctx, INTERNAL_SERVER_ERROR);
//		}
//	}
//
	private void sendError(MessageEvent e, HttpResponseStatus status) {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
		
		// write the response
		ChannelFuture future = e.getChannel().write(response);
		// close connection after the write operation is done
		future.addListener(ChannelFutureListener.CLOSE);
	}
}