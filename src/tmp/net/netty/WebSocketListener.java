package tmp.net.netty;

public interface WebSocketListener {

	public void onOpen()throws Exception;
	
	public void onClose()throws Exception;
	
	public void onMessage()throws Exception;
}
