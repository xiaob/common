package tmp.net.xsocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

public class HttpClient2 {
	
	private static final Logger log = LoggerFactory.getLogger(HttpClient2.class);
	private IBlockingConnection bc = null;
	
	public HttpClient2(){
		
	}
	
	public HttpResponse2 doGet(String url)throws IOException{
		Resource resource = new Resource(url);
		log.info("正在连接到 " + resource.getHost() + ", " + resource.getPath() + ", " + resource.getPort());
		bc = new BlockingConnection(resource.getHost(), resource.getPort());
		HttpRequest request  = new HttpRequest(resource);
		
		HttpResponse2 response = doGetRequest(request);
		HttpResponse2 realResponse = processResponse(request, response);
		
		bc.close();
		return realResponse;
	}
	
	private HttpResponse2 doGetRequest(HttpRequest request){
		try {
			bc.write(request.makeGetRequest());
			bc.flush();
		} catch (SocketTimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (BufferOverflowException e) {
			log.error(e.getMessage(), e);
		} catch (ClosedChannelException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		List<String> responseText = new ArrayList<String>();
		String line = "";
		try {
			while(!(line = bc.readStringByDelimiter(Http.SEPARATOR)).equals("")){
				responseText.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		responseText.add("");
		HttpResponse2 response = new HttpResponse2(responseText);
		try {
			int contentLength = response.getContentLength();
			log.info("response.getContentLength() = " + contentLength);
			String bodyText = bc.readStringByLength(response.getContentLength());
			response.setBodyText(bodyText);
		} catch (BufferUnderflowException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	private HttpResponse2 processResponse(HttpRequest request, HttpResponse2 response){
		if(response.isRedirect()){
			String path = response.getLocation();
			request.setPath(path);
			HttpResponse2 redirect = doGetRequest(request);
			HttpResponse2 realResponse = processResponse(request, redirect);
			return realResponse;
		}
		
		return response;
	}

}
