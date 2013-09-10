package tmp.net.xsocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse2 {
	
	private String firstLine = "";
	private Map<String, String> headerMap = new HashMap<String, String>();
	private List<String> body = new ArrayList<String>();
	private String bodyText = "";
	private List<String> defineText;
	
	public HttpResponse2(List<String> defineText){
		this.firstLine = defineText.get(0);
		boolean headerFlag = true;
		for(int i=1; i<defineText.size(); i++){
			String line = defineText.get(i);;
			if(headerFlag){
				if(line.equals("")){
					headerFlag = false;
					continue;
				}
				String[] header = line.split(Http.HEADER_SEPARATOR);
				headerMap.put(header[0].trim(), header[1].trim());
			}else{
				body.add(line);
			}
		}//for
		this.defineText = defineText;
	}
	
	//是否重定向报文
	public boolean isRedirect(){
		String responseCode = firstLine.split(" ")[1].trim();
		return responseCode.equals("301") || responseCode.equals("302");
	}
	
	public String getLocation(){
		return headerMap.get("Location");
	}
	
	public int getContentLength(){
		String contentLength = headerMap.get("Content-Length");
		if(contentLength == null){
			return 0;
		}
		return Integer.parseInt(contentLength);
	}
	
	public void setBodyText(String bodyText){
		this.bodyText = bodyText;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String line : defineText){
			sb.append(line).append(Http.SEPARATOR);
		}
		sb.append(this.bodyText);
		return sb.toString();
	}

}
