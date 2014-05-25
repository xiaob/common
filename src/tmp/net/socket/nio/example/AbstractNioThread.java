package tmp.net.socket.nio.example;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractNioThread extends Thread{

	protected Selector selector;
	protected AtomicBoolean stopping = new AtomicBoolean(false);
	
	public AbstractNioThread(String name) throws IOException{
		super(name);
		selector = Selector.open();
	}
	
	public abstract void regist(SelectableChannel channel) throws Exception;
	
	public void shutdown() throws IOException{
		stopping.set(true);
		selector.close();
	}
	
	public void run(){
		try {
			while(!stopping.get()){
				int n = selector.select();
				if(n != 0){
					Iterator<SelectionKey> it = selector.selectedKeys().iterator();
					while(it.hasNext()){
						SelectionKey key = it.next();
						it.remove();
						if(key.isValid()){
							handle(key);
						}
					}//while
				}//if
				handleFinished();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void handle(SelectionKey key) throws Exception;
	
	protected void handleFinished()throws Exception{
		
	}
	
	protected void closeKey(SelectionKey key) throws IOException{
		key.cancel();
		key.channel().close();
	}
	
}
