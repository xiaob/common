package tmp.thrift;


public class AsyncCall<T> {

	private T result;
	private final Object lock = new Object();
	
	public Future getFuture(){
		return new Future();
	}
	
	public Finisher getFinisher(){
		return new Finisher();
	}
	
	public class Future{
		public T get() throws InterruptedException {
			if(result != null){
				return result;
			}
			
			synchronized (lock) {
				lock.wait(1000 * 60 * 3); // 最多等待三分钟
			}
			
			return result;
		}
	}
	
	public class Finisher{
		public void finish(T r) {
			synchronized (lock) {
				result = r;
				lock.notifyAll();
			}
		}
	}
	
}
