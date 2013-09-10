package tmp.net.xsocket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.management.JMException;

import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IHttpResponse;
import org.xlightweb.PostRequest;
import org.xlightweb.client.HttpClient;
import org.xlightweb.client.HttpClient.FollowsRedirectMode;
import org.xlightweb.client.HttpClientConnection;
import org.xsocket.connection.ConnectionUtils;

public class XSocketHttpClient {

	public static void main(String[] args)throws Exception {
		URL urll = new URL("http://202.99.25.154:9000/apis/rest");
		show(urll.getHost() + " : " + urll.getPort());
	}
	public static void show(String message){
		System.out.println(message);
	}
	public static void test1(){
		try {
			// pass over the SSL context (here the JSE 1.6 getDefault method will be used)
//			HttpClient httpClient = new HttpClient(SSLContext.getDefault());
			HttpClient httpClient = new HttpClient();

			// make some configurations// make some configurations
			httpClient.setMaxIdle(3);                   // configure the pooling behaviour
			httpClient.setFollowsRedirectMode(FollowsRedirectMode.ALL);        // set follow redirects
			ConnectionUtils.registerMBean(httpClient);  // register the http client's mbean
			httpClient.setAutoHandleCookies(false);     // deactivates auto handling cookies

			// create a request
//			PostRequest request = new PostRequest("https://login.web.de/intern/login/", "application/x-www-form-urlencoded");
			PostRequest request = new PostRequest("http://127.0.0.1:1000", "application/x-www-form-urlencoded");
			request.setParameter("username", "me"); 
			request.setParameter("password", "I don not tell you"); 
//			request.getBody().transferTo(dataSink)
			// call it (by following redirects)
			IHttpResponse response = httpClient.call(request);

			// get the redirected response
			BodyDataSource bodyDataSource = response.getBody();
			System.out.println("响应： " + bodyDataSource.readString());
			httpClient.close();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String doGet(String url, Map<String, String> paramMap)throws IOException{
		URL u = new URL(url);
		HttpClientConnection httpClientConnection = new HttpClientConnection(u.getHost(), u.getPort());
		GetRequest request = new GetRequest(url);
		request.setHeader("Connection", "close");
		Set<String> nameSet = paramMap.keySet();
		for(String name : nameSet){
			request.setParameter(name, paramMap.get(name));
		}
		IHttpResponse response = httpClientConnection.call(request);
		BodyDataSource bodyDataSource = response.getBody();
		String bodyText = bodyDataSource.readString();
		httpClientConnection.close();
		return bodyText;
	}
	public static String doGet1(String url, Map<String, String> paramMap)throws IOException{
		HttpClient httpClient = new HttpClient();
		httpClient.setMaxIdle(3);                   
		httpClient.setFollowsRedirectMode(FollowsRedirectMode.ALL);        
		httpClient.setAutoHandleCookies(false);    
		
		GetRequest request = new GetRequest(url);
		request.setHeader("Connection", "close");
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
	public static String doPost1(String url, Map<String, String> paramMap)throws IOException{
		HttpClient httpClient = new HttpClient();
		httpClient.setMaxIdle(3);                   
		httpClient.setFollowsRedirectMode(FollowsRedirectMode.ALL);        
		httpClient.setAutoHandleCookies(false);    
		
		PostRequest request = new PostRequest(url, "application/x-www-form-urlencoded");
		request.setHeader("Connection", "close");
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

}
