package com.yuan.common.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.yuan.common.util.ReflectUtil;

public class AsyncService {
	
	private ExecutorService pool = null;
	
	public AsyncService(int nThreads){
		pool = Executors.newFixedThreadPool(nThreads);
	}
	
	public static void main(String args[])throws Exception{
		AsyncService service = new AsyncService(2);
		Future<String> rFuture = service.asyncMethod(String.class, new String("1111"), "toString");
		service.asyncMethod(null, new String("1111"), "toString");
		System.out.println("====================");
		System.out.println(rFuture.get());
		service.shutdown();
	}
	
	public void shutdown() {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}

	}
	
	public <T extends Object> Future<T> asyncMethod(Class<T> clazz, Object obj, String methodName, Object... params){
		if(clazz == null){
			pool.submit(new DefaultRunnable(obj, methodName, params));
			return null;
		}
		return pool.submit(new DefaultCallable<T>(obj, methodName, params));
	}
	
	public static <V extends Object> V call(Callable<V> call)throws InterruptedException, ExecutionException{
		ExecutorService service = Executors.newSingleThreadExecutor();
		V result = service.submit(call).get();
		service.shutdownNow();
		return result;
	}
	
	public static <V extends Object> V call(Callable<V> call, long timeout, TimeUnit unit)throws InterruptedException, ExecutionException, TimeoutException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		V result = service.submit(call).get(timeout, unit);
		service.shutdownNow();
		return result;
	}

}

class DefaultCallable<V> implements Callable<V>{
	
	private Object obj;
	private String methodName;
	private Object[] params;
	
	public DefaultCallable(Object obj, String methodName, Object... params){
		this.obj = obj;
		this.methodName = methodName;
		this.params = params;
	}
	@SuppressWarnings("unchecked")
	public V call()throws Exception{
		Object oo = ReflectUtil.execMethod(obj, methodName, params);
		if(oo == null){
			return null;
		}
		return (V)oo;
	}

}

class DefaultRunnable implements Runnable{
	private Object obj;
	private String methodName;
	private Object[] params;
	
	public DefaultRunnable(Object obj, String methodName, Object... params){
		this.obj = obj;
		this.methodName = methodName;
		this.params = params;
	}
	public void run(){
		try {
			ReflectUtil.execMethod(obj, methodName, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
