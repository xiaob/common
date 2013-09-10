package com.yuan.common.message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MessageBus {

	private static final Logger LOG = LoggerFactory.getLogger(MessageBus.class);
			
	private static final class Holder{
		public static final MessageBus INSTANCE = new MessageBus();
	}
	
	public static MessageBus getInstance(){
		return Holder.INSTANCE;
	}
	
	public static interface MessageListener{
		public void onMessage(Object... message)throws Exception;
	}
	
	public static void publish(String topic, Object message){
		getInstance().publishMessage(topic, message);
	}
	
	public static void subscribe(String topic, MessageListener listener){
		getInstance().subscribeMessage(topic, listener);
	}
	
	public static void shutdown(){
		getInstance().shutdownNow();
	}
	
	private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private ConcurrentMap<String, List<MessageListener>> listenerMap = new ConcurrentHashMap<String, List<MessageListener>>();
			
	private MessageBus(){
		
	}
	
	public void publishMessage(String topic, final Object... message){
		if(listenerMap.containsKey(topic)){
			for(final MessageListener listener : listenerMap.get(topic)){
				executorService.submit(new Runnable() {
					@Override
					public void run() {
						try {
							listener.onMessage(message);
						} catch (Exception e) {
							LOG.warn(e.getMessage(), e);
						}
					}
				});
			}//for
		}//if
	}
	
	public void subscribeMessage(String topic, MessageListener listener){
		if(!listenerMap.containsKey(topic)){
			listenerMap.putIfAbsent(topic, new ArrayList<MessageListener>());
		}
		
		listenerMap.get(topic).add(listener);
	}
	
	public void shutdownNow(){
		executorService.shutdown();
	}
	
}
