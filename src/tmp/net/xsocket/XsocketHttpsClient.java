package tmp.net.xsocket;

import javax.net.ssl.SSLContext;

import org.xlightweb.BodyDataSource;
import org.xlightweb.IHttpResponse;
import org.xlightweb.PostRequest;
import org.xlightweb.client.HttpClient;
import org.xsocket.connection.ConnectionUtils;

public class XsocketHttpsClient {

	public static void main(String[] args)throws Exception {
		// pass over the SSL context (here the JSE 1.6 getDefault method will be used)
		HttpClient httpClient = new HttpClient(SSLContext.getDefault());

		// make some configurations// make some configurations
		httpClient.setMaxIdle(3);                   // configure the pooling behaviour
		httpClient.setFollowsRedirect(true);        // set follow redirects
		ConnectionUtils.registerMBean(httpClient);  // register the http client's mbean
		httpClient.setAutoHandleCookies(false);     // deactivates auto handling cookies

		// create a request


		PostRequest request = new PostRequest("https://login.web.de/intern/login/", "application/x-www-form-urlencoded");
//		request.setHeader(headername, headervalue)
		request.setParameter("username", "me"); 
		request.setParameter("password", "I don not tell you"); 
		        
		// call it (by following redirects)
		IHttpResponse response = httpClient.call(request);

		// get the redirected response
		BodyDataSource bodyDataSource = response.getBody();
//		bodyDataSource.transferTo(dataSink)
		//...

	}

}
