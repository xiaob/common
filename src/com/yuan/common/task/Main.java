package com.yuan.common.task;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	
	
	public static void main(String[] args)throws Exception {
		test3();
		
	}
	
	public static void test1()throws Exception{
		TriggerFactory tf = new TriggerFactory("");
		Task task = new Task("");
		task.addTask(SimpleQuartzJob.class, tf.makeSimpleTrigger(new Date(), 1000, 5), null);
		task.start();
		
		Thread.sleep(1000 * 4);
		
		task.addTask(SimpleQuartzJob.class, tf.makeSimpleTrigger(new Date(), 1000, 5), null);
		task.addTask(SimpleQuartzJob.class, tf.makeSimpleTrigger(new Date(), 1000, 5), null);
	}
	
	public static void test2(){
		AtomicInteger count = new AtomicInteger(0);
		System.out.println(count.incrementAndGet());
		System.out.println(count.incrementAndGet());
		System.out.println(count.incrementAndGet());
	}
	public static void test3(){
		TriggerFactory tf = new TriggerFactory("test3");
		Task task = new Task("test3");
		task.addTask(SimpleQuartzJob.class, tf.makeCronTrigger(new Date(), "0 * * * * ?"), null);
		task.start();
	}
	
}
