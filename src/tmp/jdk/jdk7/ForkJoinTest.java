package tmp.jdk.jdk7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();

		int r = pool.submit(new Fibonacci(20)).get();
		System.out.println(r);

		pool.shutdown();
	}

}

// 递归计算
class Fibonacci extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;

	private final int n;

	public Fibonacci(int n) {
		this.n = n;
	}

	public Integer compute() {
		System.out.println(Thread.currentThread().getName());
		if (n <= 1)
			return n;
		
		Fibonacci f1 = new Fibonacci(n - 1);
		f1.fork();
		
		Fibonacci f2 = new Fibonacci(n - 2);
		f2.fork();
		
		return f1.join() + f2.join();
	}
}
