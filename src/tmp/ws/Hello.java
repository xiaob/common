package tmp.ws;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://huoxing.com/service")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Hello {

	public String say(String message){
		System.out.println(message);
		return "qqqqqqqq";
	}
	
}
