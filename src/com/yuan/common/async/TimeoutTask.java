package com.yuan.common.async;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.collection.DelayItem;

public abstract class TimeoutTask<T> implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(TimeoutTask.class);
	
	private DelayQueue<DelayItem<T>> timeoutQueue = new DelayQueue<DelayItem<T>>(); //超时队列
	private Thread daemonThread;
	private AtomicBoolean running = new AtomicBoolean(true);
	private String name;
	private Object lock = new Object();

	public TimeoutTask(String name){
		this.name = name;
	}
	
	public void start(){
		if(daemonThread == null){
			daemonThread = new Thread(this);
	        daemonThread.setDaemon(true);
	        daemonThread.setName(name);
	        daemonThread.start();
		}
	}
	
	public void stop(){
		if(daemonThread != null){
			running.compareAndSet(true, false);
			daemonThread.interrupt();
		}
	}
	
	public void join() throws InterruptedException{
		if(daemonThread != null){
			daemonThread.join();
		}
	}
	
	@Override
	public void run() {
		while(running.get()) {
			try {
				if(timeoutQueue.isEmpty()){//如果超时队列是空的就阻塞守护线程
					lock();
				}
				DelayItem<T> delayItem = timeoutQueue.take();
				if(delayItem == null){
					continue;
				}
				
				try {
					process(delayItem.getItem());
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				}
				
				//重新开始延迟
				delayItem.resetTime();
				timeoutQueue.put(delayItem);
			} catch (InterruptedException e) {
				log.warn(e.getMessage(), e);
			}
		}

	}
	protected void lock() throws InterruptedException{
		synchronized(lock){
			lock.wait();
		}
	}
	protected void unlock(){
		synchronized(lock){
			lock.notifyAll();
		}
	}
	
	protected abstract void process(T submit)throws Exception;
	
	public void put(T submit, long timeout, TimeUnit unit){
		long nanoseconds = TimeUnit.NANOSECONDS.convert(timeout, unit);
		
		timeoutQueue.put(new DelayItem<T>(submit, nanoseconds));
		unlock();
	}
	
	public void remove(T submit){
		DelayItem<T> delayItem = getDelayItem(submit);
		
		if(delayItem != null){
			timeoutQueue.remove(delayItem);
		}
	}
	
	public void resetTime(T submit){
		DelayItem<T> delayItem = getDelayItem(submit);
		
		if(delayItem != null){
			delayItem.resetTime();
		}
		
	}
	
	protected DelayItem<T> getDelayItem(T submit){
		
		Iterator<DelayItem<T>> it = timeoutQueue.iterator();
		while(it.hasNext()){
			DelayItem<T> delayItem = it.next();
			if(delayItem.getItem().equals(submit)){
				
				return delayItem;
			}
		}
		
		return null;
	}

}
