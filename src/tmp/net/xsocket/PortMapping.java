package tmp.net.xsocket;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xlightweb.BadMessageException;
import org.xlightweb.BlockingBodyDataSource;
import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpRequestHandler;
import org.xlightweb.IHttpResponse;
import org.xlightweb.client.HttpClient;
import org.xlightweb.server.HttpServer;

public class PortMapping {
	
	private static final Logger log = LoggerFactory.getLogger(PortMapping.class);
	
	public static final int LOCALPORT = 9000;
	public static final String TARGETHOST = "www.google.com.cn";
	public static final int TARGETPORT = 80;

	public static void main(String[] args)throws Exception {
		System.setProperty("org.xlightweb.showDetailedError", "true");
		HttpServer srv = new HttpServer(PortMapping.LOCALPORT, new MethodInfoHandler());
		log.info("端口转发程序启动, 开始 监听" + LOCALPORT + "端口 ... ...");
		srv.run();
		log.info("================");
//		srv.start();
		
	}

}

class MethodInfoHandler implements IHttpRequestHandler {
	
	   public void onRequest(IHttpExchange exchange) throws IOException, BadMessageException {

	      IHttpRequest req = exchange.getRequest();
	      String host = PortMapping.TARGETHOST + ":" + PortMapping.TARGETPORT;
	      req.setHost(host);
	      HttpClient httpClient = new HttpClient();
	      IHttpResponse response = httpClient.call(req);
	   
	      // ... and send it back to the client
//	      String bodyText = getBodyText(response);
//	      logger.info(bodyText);
//	      bodyText = processBodyText(bodyText);
//	      
//	      IHttpResponseHeader h = response.getResponseHeader();
//	      logger.info(h.toString());
//	      logger.info(bodyText);
//	      h.setHeader("Content-Encoding", "");
//	      h.setContentLength(bodyText.length());
//	      BodyDataSink bds = exchange.send(h);
//	      bds.setEncoding(h.getCharacterEncoding());
//	      bds.write(bodyText);
//	      bds.close();
	      exchange.send(response);
	   }
	   
	   private String processBodyText(String bodyText){
			String regex = "";
//			regex = "<a\\s*href=([^>]*)\\s*[^>]*>";
			regex = "(http://|https://){1}[\\w\\.\\-/:]+";
		   	Pattern p = Pattern.compile(regex);
			 Matcher m = p.matcher(bodyText);
			 StringBuffer sb = new StringBuffer();
			 for(int i=1;m.find();i++){
				 String a = m.group();
				 String host = PortMapping.TARGETHOST + ":" + PortMapping.TARGETPORT;
//				 System.out.println("===" + a);
//				 System.out.println("***" + replaceHost(a, host));
				 m.appendReplacement(sb, replaceHost(a, host));
			 }
			 m.appendTail(sb);
			return sb.toString();
	   }
		
	   private String replaceHost(String url, String host){
		   StringBuilder sb = new StringBuilder();
		   Pattern p = Pattern.compile("//[^/]*/");
		   String[] r = p.split(url);
		   sb.append(r[0]).append("//");
		   sb.append(host).append("/");
		   if(r.length > 1){
			   sb.append(r[1]);
		   }
		   return sb.toString();
	   }
	   
	   private String getBodyText(IHttpResponse response){
		   String body  = "";
			try {
				BlockingBodyDataSource bodyDataSource = response.getBlockingBody();
				body = bodyDataSource.readString();
				bodyDataSource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return body;
	   }
	   
	}
