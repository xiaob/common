package tmp.disruptor;

import com.lmax.disruptor.EventFactory;

public class MessageEvent {

	public static EventFactory<MessageEvent> FACTORY = new EventFactory<MessageEvent>(){
		public MessageEvent newInstance() {
			return new MessageEvent();
		}
	};
	
	private Object message;

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
}
