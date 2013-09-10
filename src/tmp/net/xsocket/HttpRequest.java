package tmp.net.xsocket;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
	
	private String path = "";
	private List<HttpHeader> headerList = new ArrayList<HttpHeader>();
	private List<String> body = new ArrayList<String>();
	
	public HttpRequest(){
		
	}
	
	public HttpRequest(Resource resource){
		this(resource.getHost(), resource.getPort(), resource.getPath());
	}
	
	public HttpRequest(String host, int port, String path){
		this.addHost(host, port);
		this.path = path;
	}
	
	public void addHeader(String name, String value){
		headerList.add(new HttpHeader(name, value));
	}
	
	public void addHost(String host, int port){
		String url = host +":"+ port + Http.SEPARATOR;
		this.addHeader("Host", url);
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String makeGetRequest(){
		StringBuilder sb = new StringBuilder();
		sb.append("GET "+ path +" HTTP/1.1" + Http.SEPARATOR);
		for(HttpHeader hh : headerList){
			sb.append(hh.getName() + ": " + hh.getValue());
		}
		sb.append(Http.SEPARATOR);
		return sb.toString();
	}

}
