package tmp.algorithm;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThread1 {

	public static AtomicInteger NUMBER = new AtomicInteger(0);
	public static AtomicBoolean FLAG = new AtomicBoolean(true);
	
	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		a.start();

		ThreadB b = new ThreadB();
		b.start();
	}

}

class ThreadA extends Thread{
	
	public void run(){
		for(int i=0; i<10;){
			if(TestThread1.FLAG.get()){
				TestThread1.NUMBER.incrementAndGet();
				System.out.println("ThreadA ++");
				i++;
				TestThread1.FLAG.set(false);
			}else{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
class ThreadB extends Thread{
	
	public void run(){
		while(true){
			if(TestThread1.FLAG.get() == false){
				System.out.println("ThreadB = " + TestThread1.NUMBER);
				TestThread1.FLAG.set(true);
			}else{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

