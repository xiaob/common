package tmp.thrift;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * SyncValue用于在两个线程间传递数据
 * 与SynchronousQueue的区别是：
 *    1.SynchronousQueue的内部容量为0, 而SyncValue的内部容量为1
 *    2.使用SynchronousQueue的投递线程和接收线程都有可能会阻塞, 
 *      而使用SyncValue的接收线程有可能会阻塞, 投递线程永远不会阻塞
 * @author yuan<cihang.yuan@happyelements.com>
 *
 * @param <T>
 */
public class SyncValue<T> {

	private T result;
	private AtomicBoolean finish = new AtomicBoolean(false); 
	private Object lock = new Object();
	
	public T get(long timeout, TimeUnit unit) throws InterruptedException {
		synchronized (lock) {
			if(finish.get()){
				return result;
			}
			// 必须设置超时, 不能无限等下去
			lock.wait(TimeUnit.MILLISECONDS.convert(timeout, unit));
		}
		
		return result;
	}
	
	public void put(T t) {
		synchronized (lock) {
			result = t;
			finish.set(true);
			lock.notifyAll();
		}
	}
	
}
