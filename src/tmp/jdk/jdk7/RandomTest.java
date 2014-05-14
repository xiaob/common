package tmp.jdk.jdk7;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

	public static void main(String[] args) {
		// Random对象是线程安全的 r.nextInt(10)
		
		// 每个线程一个ThreadLocalRandom实例
		ThreadLocalRandom r = ThreadLocalRandom.current();
		
		for(int i=0; i<10; i++){
			System.out.println(r.nextDouble());
		}
	}

}
