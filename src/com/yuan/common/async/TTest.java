package com.yuan.common.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TTest {

	public static void main(String[] args)throws Exception {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		ScheduledFuture<?> future = service.scheduleAtFixedRate(new T1(), 0, 1, TimeUnit.SECONDS);
		
		TimeUnit.SECONDS.sleep(10);
		future.cancel(true);
		service.shutdownNow();
	}

}

class T1 implements Runnable{
	
	private int i=0;
	public void run(){
		i++;
		System.out.println(i);
	}
	
}
