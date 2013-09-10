package tmp.net.xsocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.xlightweb.BadMessageException;
import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.HttpResponse;
import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpRequestHandler;
import org.xlightweb.IHttpResponse;
import org.xlightweb.client.HttpClient;
import org.xlightweb.client.HttpClient.FollowsRedirectMode;
import org.xlightweb.server.HttpServer;

import com.yuan.common.util.DateUtil;

public class HxbosClient {

	public static void main(String[] args)throws Exception {
		String secretCode = "2c7949a7-32a1-4d46-ad76-795946957df0";
		Map<String, String> request = new HashMap<String, String>();
		request.put("method", "hello");
		request.put("version", "1.0");
		request.put("session", "1");
		request.put("appKey", "821f5807-1b59-4057-b6cf-4db48ad9b1cf");
		request.put("timestamp", DateUtil.format(new Date(), "yyyyMMddHHmmss"));
		request.put("transactionId", "d94f4a53-7360-4b94-a9ae-76ae54b166ce");
		request.put("asyncMode", "true");
		request.put("callbackUrl", "http://192.168.1.102:1000/");
		String sign = getSign(request, secretCode);
		request.put("sign", sign);
		
		startReceive(1000);
		String r = doGet("http://192.168.5.29:9001/Apis/rest", request);
		System.out.println(r);
	}
	
	public static String doGet(String url, Map<String, String> paramMap)throws IOException{
		HttpClient httpClient = new HttpClient();
		httpClient.setMaxIdle(3);                   
		httpClient.setFollowsRedirectMode(FollowsRedirectMode.ALL);        
		httpClient.setAutoHandleCookies(false);    

		GetRequest request = new GetRequest(url);
		Set<String> nameSet = paramMap.keySet();
		for(String name : nameSet){
			request.setParameter(name, paramMap.get(name));
		}
		IHttpResponse response = httpClient.call(request);
		BodyDataSource bodyDataSource = response.getBody();
		String bodyText = bodyDataSource.readString();
		httpClient.close();
		return bodyText;
	}
	
	public static String getSign(Map<String, String> paramMap, String secretCode){
		List<String> paramNameList = new ArrayList<String>();
		Set<String> paramNameSet = paramMap.keySet();
		for(String paramName : paramNameSet){
			paramNameList.add(paramName);
		}
		Collections.sort(paramNameList); //升序排序
		StringBuilder params = new StringBuilder();
		params.append(secretCode);
		for(String paramName : paramNameList){
			params.append(paramName);
			params.append(paramMap.get(paramName));
		}
		params.append(secretCode);
		
		try {
			String sign = DigestUtils.md5Hex(params.toString().getBytes("UTF-8")).toUpperCase();
			return sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void startReceive(int port){
		try {
			HttpServer srv = new HttpServer(port, new HxbosServerHandler());
			srv.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}

class HxbosServerHandler implements IHttpRequestHandler {

	public void onRequest(IHttpExchange exchange) throws IOException,
			BadMessageException {

		IHttpRequest req = exchange.getRequest();
		System.out.println(" ---> IP: " + req.getRemoteAddr() + ", port: " + req.getRemotePort());
		System.out.println("method: " + req.getMethod() + ", path : " + req.getRequestURI() + ", contentType: " + req.getContentType());
		Set<String> paramNameSet = req.getParameterNameSet();
		for(String paramName : paramNameSet){
			System.out.println(paramName + " = " + req.getParameter(paramName));
		}
		
		BodyDataSource bodyDataSource = req.getBody();
		String bodyText = bodyDataSource.readString();
		System.out.println(bodyText);
		IHttpResponse resp = new HttpResponse("text/plain", "called method=" + req.getMethod());
		exchange.send(resp);
	}
}
