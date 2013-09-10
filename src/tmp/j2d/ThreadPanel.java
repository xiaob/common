package tmp.j2d;

import java.awt.Color;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public abstract class ThreadPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	protected Thread t;
	protected AtomicBoolean running = new AtomicBoolean(true);
	
	public ThreadPanel(int width, int height){
		//设置组件首选大小
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
		t = new Thread(this);
	}
	
	public void start(){
		t.start();
	}
	
	public void shutdown(){
		if(t != null){
			running.compareAndSet(true, false);
			t.interrupt();
		}
	}
	
	public void join()throws InterruptedException{
		if(t != null){
			t.join();
		}
	}
	
	public void run(){
		while(running.get()){
			try {
				call();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void call()throws InterruptedException;
		
}
