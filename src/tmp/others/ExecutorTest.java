package tmp.others;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for(int i=0; i<3; i++){
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.MINUTES.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		ThreadPoolExecutor t = (ThreadPoolExecutor)executorService;
		System.out.println("t.getQueue().size() = " + t.getQueue().size());
		System.out.println("t.getTaskCount() = " + t.getTaskCount());
	}

}


