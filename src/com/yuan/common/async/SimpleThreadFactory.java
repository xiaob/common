package com.yuan.common.async;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单线程工厂, 用来产生可指定命名的线程
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class SimpleThreadFactory implements ThreadFactory{

	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	
	public SimpleThreadFactory(String systemName, String moduleName, String poolName){
		SecurityManager localSecurityManager = System.getSecurityManager();
		this.group = ((localSecurityManager != null) ? localSecurityManager.getThreadGroup() : Thread.currentThread().getThreadGroup());
		this.namePrefix = systemName + "-" +  moduleName + "-" + poolName + "-";
	}
	
	@Override
	public Thread newThread(Runnable runnable) {
		Thread localThread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
		if (localThread.isDaemon())
			localThread.setDaemon(false);
		if (localThread.getPriority() != Thread.NORM_PRIORITY)
			localThread.setPriority(Thread.NORM_PRIORITY);
		return localThread;
	}

}
