package tmp.disruptor;

import com.lmax.disruptor.EventFactory;

public class MyEvent {

	public static EventFactory<MyEvent> FACTORY = new EventFactory<MyEvent>(){
		public MyEvent newInstance() {
			return new MyEvent();
		}
	};
	
	private String eventName;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
