package tmp.net.xsocket;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.StringUtil;

public class Resource {
	
	private static final Logger log = LoggerFactory.getLogger(Resource.class);
	
	private String host;
	private int port;
	private String path;
	
	public Resource(){
		
	}
	
	public Resource(String url){
		try {
			URL u = new URL(url);
			this.host = u.getHost();
			this.port = u.getPort();
			if(port == -1){
				port = 80;
			}
			this.path = u.getPath();
			if(!StringUtil.hasText(path)){
				path = "/";
			}
		} catch (MalformedURLException e) {
			log.info(e.getMessage(), e);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
