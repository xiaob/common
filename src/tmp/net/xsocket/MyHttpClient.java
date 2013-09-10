package tmp.net.xsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.xlightweb.BlockingBodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpResponse;
import org.xlightweb.PostRequest;
import org.xlightweb.client.HttpClient;
import org.xsocket.connection.ConnectionUtils;

public class MyHttpClient {
	
	private HttpClient httpClient;
	
	public MyHttpClient(){
		this(false);
	}
	
	public MyHttpClient(boolean ssl){
		if(ssl){
			try {
				httpClient = new HttpClient(SSLContext.getDefault());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}else{
			httpClient = new HttpClient();
		}
	}
	
	public String doPost(String url, String[] formParams)throws Exception{
		// make some configurations// make some configurations
		httpClient.setMaxIdle(3);                   // configure the pooling behaviour
		httpClient.setTreat302RedirectAs303(true);  // redirect 302 post response by a get request
		httpClient.setFollowsRedirect(true);        // set follow redirects
		ConnectionUtils.registerMBean(httpClient);  // register the http client's mbean
		httpClient.setAutoHandleCookies(false);     // deactivates auto handling cookies

		// create a request
		if(formParams == null){
			formParams = new String[]{};
		}
		PostRequest request = new PostRequest(url, formParams);

		// call it (by following redirects)
		IHttpResponse response = httpClient.call(request);
		return getResponseText(response);
	}
	
	public String doGet(String url)throws Exception{
		IHttpRequest request = new GetRequest(url);
		IHttpResponse response = httpClient.call(request);
		return getResponseText(response);
	}
	
	private String getResponseText(IHttpResponse response){
		String firstLine = response.getProtocol() + " " + response.getStatus() + " " + response.getReason() + "\r\n";
		Set<String> header = response.getHeaderNameSet();
		StringBuilder sb = new StringBuilder();
		for(String name : header){
			sb.append(name + ": " + response.getHeader(name) + "\r\n");
		}
		String body  = "";
		try {
			BlockingBodyDataSource bodyDataSource = response.getBlockingBody();
			body = bodyDataSource.readString();
			bodyDataSource.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return firstLine + sb.toString() + body;
	}
	
	private String processBodyText(String bodyText){
		String regex = "";
//		regex = "<a\\s*href=([^>]*)\\s*[^>]*>";
		regex = "(http://|https://){1}[\\w\\.\\-/:]+";
	   	Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(bodyText);
		 StringBuffer sb = new StringBuffer();
		 for(int i=1;m.find();i++){
			 String a = m.group();
			 String host = PortMapping.TARGETHOST + ":" + PortMapping.TARGETPORT;
//			 System.out.println("===" + a);
//			 System.out.println("***" + replaceHost(a, host));
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
	
	public static void main(String[] args)throws Exception {
		String[] formParams = new String[] {
				"userCode=admin", 
        		"userPassword=cq_adc@ddxrb",
        		"saveUserCode=1"
		};
		MyHttpClient hc = new MyHttpClient();
		String r = hc.doGet("http://www.google.com");
//		String r = hc.doPost("http://www.1258299.com/page/loginCheck.action", formParams);
		System.out.println(r);
//		hc.processBodyText(r);
//		testurl();
		
	}
	
	public static void testurl()throws Exception{
		URL url = new URL("http://www.baidu.com");
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while((line = br.readLine())!=null){
			System.out.println(line);
		}
		is.close();
	}
	
}
