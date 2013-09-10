package tmp.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestLock {

	public static void main(String args[]){
		test2();
		ConcurrentHashMap<String, String> map;
	}
	
	public static void test1(){
		final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		final Lock writeLock = readWriteLock.writeLock();
		final Lock readLock = readWriteLock.readLock();
		
		writeLock.lock();
		readLock.lock();
		
		writeLock.unlock();
		readLock.unlock();
	}
	
	public static void test2(){
		My my = new My();
		my.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		my.unlock();
	}
}

class My extends Thread{
	
	private Object lock = new Object();
	
	public void run(){
		try {
			System.out.println("111111111");
			synchronized (lock) {
				lock.wait();
			}
			System.out.println("2222222222");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void unlock(){
		System.out.println("33333333");
		synchronized (lock) {
			lock.notifyAll();
		}
		System.out.println("4444444");
	}
}
