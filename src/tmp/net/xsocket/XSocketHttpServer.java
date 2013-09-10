package tmp.net.xsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import org.xlightweb.BadMessageException;
import org.xlightweb.HttpResponse;
import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpRequestHandler;
import org.xlightweb.IHttpResponse;
import org.xlightweb.server.HttpServer;

public class XSocketHttpServer {

	public static void main(String[] args) {
		// activates detailed message output for debugging purposes
		System.setProperty("org.xlightweb.showDetailedError", "true");

		try {
			// creates the server by passing over the port number & the server handler
			HttpServer srv = new HttpServer(1000, new DefaultServerHandler());
			 
			// run it
//			srv.run();

			// ... or start it by using a dedicated thread
			srv.start(); // returns after the server has been started
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class DefaultServerHandler implements IHttpRequestHandler {

	public void onRequest(IHttpExchange exchange) throws IOException,
			BadMessageException {

		IHttpRequest req = exchange.getRequest();
		System.out.println("client ---> IP: " + req.getRemoteAddr() + ", port: " + req.getRemotePort());
		System.out.println("method: " + req.getMethod() + ", path : " + req.getRequestURI() + ", contentType: " + req.getContentType());
		Set<String> paramNameSet = req.getParameterNameSet();
		for(String paramName : paramNameSet){
			System.out.println(paramName + " = " + req.getParameter(paramName));
		}
		System.out.println("===========================================");
		Set<String> headerNameSet = req.getHeaderNameSet();
		for(String headerName : headerNameSet){
			System.out.println(headerName + " : " + req.getHeader(headerName));
		}
		System.out.println();
		BufferedReader bodyReader = new BufferedReader(req.getBody().toReader());
		String line = null;
		while((line = bodyReader.readLine())!= null){
			System.out.println("body : " + line);
		}
		System.out.println("===========================================");
		// create a response object
		IHttpResponse resp = new HttpResponse("text/plain", "called method=" + req.getMethod());

		// ... and send it back to the client
		exchange.send(resp);
	}
}
