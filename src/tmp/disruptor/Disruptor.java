package tmp.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;

public class Disruptor {
	
	private List<MessageEventWorkHandler> messageEventList = new ArrayList<MessageEventWorkHandler>();
	private WorkerPool<MessageEvent> workerPool;
	private RingBuffer<MessageEvent> ringBuffer;
	private ExecutorService executor;
	
	public void start(){
		start(Executors.newCachedThreadPool());
	}
	
	public void start(ExecutorService executor){
		MessageEventWorkHandler[] workHandlers = new MessageEventWorkHandler[messageEventList.size()];
		for(int i=0; i<messageEventList.size(); i++){
			workHandlers[i] = messageEventList.get(i);
		}
		workerPool = new WorkerPool<MessageEvent>(MessageEvent.FACTORY,
	                new FatalExceptionHandler(),
	                workHandlers);
		ringBuffer = workerPool.start(executor);
		this.executor = executor;
	}
	
	public void addWorkHandler(MessageEventWorkHandler workHandler){
		messageEventList.add(workHandler);
	}
	
	public void publishEvent(Object message){
		long sequence = ringBuffer.next();
		ringBuffer.get(sequence).setMessage(message);
		ringBuffer.publish(sequence);
	}
	
	public void shutdown(){
		if(workerPool != null){
			workerPool.drainAndHalt();
		}
		if(executor != null){
			executor.shutdown();
		}
	}
	
}
