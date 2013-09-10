package tmp.algorithm;

import java.util.concurrent.SynchronousQueue;

public class TestThread2 {

	public static void main(String[] args) {
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
		new A(queue).start();
		new B(queue).start();
	}

}

class A extends Thread{
	private SynchronousQueue<Integer> queue;
	
	public A(SynchronousQueue<Integer> queue){
		this.queue = queue;
	}
	
	public void run(){
		try {
			for(int i=1; i<=1000; i++){
				queue.put(i);
			}
			queue.put(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class B extends Thread{
	private SynchronousQueue<Integer> queue;
	
	public B(SynchronousQueue<Integer> queue){
		this.queue = queue;
	}
	
	public void run(){
		try {
			while(true){
				int i = queue.take();
				if(i == -1){
					break;
				}else{
					System.out.println("B = " + i);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}