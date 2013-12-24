package tmp.disruptor;

import java.util.concurrent.Executors;

import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceGroup;
import com.lmax.disruptor.WorkerPool;

public class Demo1 {

	public static void main(String[] args) {
		test2();
	}
	
	public static void test1(){
//		EventHandler<MyEvent> handler1 = new EventHandler<MyEvent>() {
//			public void onEvent(MyEvent event, long sequence, boolean endOfBatch)
//					throws Exception {
//				show("MyEvent=" + event.getEventName() + ", sequence=" + sequence + ", endOfBatch=" + endOfBatch);
//			}
//		};
		MyEventWorkHandler workHandler1 = new MyEventWorkHandler(){
			public void onEvent(MyEvent event) throws Exception {
				show("event1 = " + event.getEventName());
			}
		};
		MyEventWorkHandler workHandler2 = new MyEventWorkHandler(){
			public void onEvent(MyEvent event) throws Exception {
				show("event2 = " + event.getEventName());
			}
		};
		MyEventWorkHandler[] myEventWorkHandlers = new MyEventWorkHandler[2];
		myEventWorkHandlers[0] = workHandler1;
		myEventWorkHandlers[1] = workHandler2;
		
	    WorkerPool<MyEvent> workerPool = new WorkerPool<MyEvent>(MyEvent.FACTORY,
                new FatalExceptionHandler(),
                myEventWorkHandlers);
	    RingBuffer<MyEvent> ringBuffer = workerPool.start(Executors.newCachedThreadPool());
		
//		RingBuffer<MyEvent> ringBuffer = new RingBuffer<MyEvent>(MyEvent.FACTORY, 4, ClaimStrategy.Option.SINGLE_THREADED, WaitStrategy.Option.YIELDING);
//		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier(new Sequence(3));
		
//		BatchEventProcessor<MyEvent> batchProcessorFizz = new BatchEventProcessor<MyEvent>(ringBuffer, sequenceBarrier, handler1);
//		Executors.newCachedThreadPool().execute(batchProcessorFizz);
		
		SequenceGroup gatingSequenceGroup = new SequenceGroup();
		gatingSequenceGroup.add(new Sequence(1));
		gatingSequenceGroup.add(new Sequence(2));
		gatingSequenceGroup.add(new Sequence(3));
		gatingSequenceGroup.add(new Sequence(4));
		ringBuffer.addGatingSequences(new Sequence());
		
		publishEvent1(ringBuffer, "0000");
//		publishEvent1(ringBuffer, "1111");
//		publishEvent1(ringBuffer, "2222");
//		publishEvent1(ringBuffer, "3333");
//		publishEvent1(ringBuffer, "4444");
//		publishEvent1(ringBuffer, "5555");
//		for(int i=0; i<20; i++){
//			publishEvent(ringBuffer, "event-" + i);
//		}
	}
	public static void publishEvent(RingBuffer<MyEvent> ringBuffer, String eventName){
		long sequence = ringBuffer.next();
		ringBuffer.get(sequence).setEventName(eventName);
		show("sequence = " + sequence);
		ringBuffer.publish(sequence);
	}
	public static void publishEvent1(RingBuffer<MyEvent> ringBuffer, String eventName){
		ringBuffer.get(1).setEventName(eventName);
		ringBuffer.publish(1);
	}
	public static void show(String message){
		System.out.println(message);
	}
	
	public static void test2(){
		Disruptor disruptor = new Disruptor();
		disruptor.addWorkHandler(new MessageEventWorkHandler() {
			public void onEvent(MessageEvent event) throws Exception {
				show(event.getMessage().toString());
			}
		});
		disruptor.addWorkHandler(new MessageEventWorkHandler() {
			public void onEvent(MessageEvent event) throws Exception {
				show("==" + event.getMessage().toString());
			}
		});
		disruptor.addWorkHandler(new MessageEventWorkHandler() {
			public void onEvent(MessageEvent event) throws Exception {
				show("**" + event.getMessage().toString());
			}
		});
		disruptor.start();
		
		disruptor.publishEvent("111");
		disruptor.publishEvent("222");
		disruptor.publishEvent("3");
		disruptor.publishEvent("4");
		
		disruptor.shutdown();
	}

}

