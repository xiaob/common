package tmp.jdk.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class ConcurrentTest {

	public static void main(String[] args) {
		// test1();
		// test2();
		// test3();
		test4();
	}

	public static void test1() {
		LongAdder longAdder = new LongAdder();
		longAdder.increment();
		System.out.println(longAdder.longValue());

		DoubleAdder doubleAdder = new DoubleAdder();
		doubleAdder.add(1);
		System.out.println(doubleAdder.doubleValue());

		LongAccumulator longAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
		longAccumulator.accumulate(10);
		System.out.println(longAccumulator.longValue());
	}

	public static void test2() {
		Random random = new Random();
		int count = 1000;
		int[] array = new int[count];
		Arrays.parallelSetAll(array, (index) -> random.nextInt());
		int[] copy = new int[count];
		System.arraycopy(array, 0, copy, 0, array.length);
		Arrays.parallelSort(array);
		Arrays.sort(copy);
	}

	public static void test3() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		// lambda表达式
		service.submit(() -> {
			System.out.println("1111111111");
		});
		// 方法引用
		service.submit(ConcurrentTest::run);
		service.shutdownNow();

		A.a1();
		new A() {
		}.foo();
	}

	private static void run() {
		System.out.println("2222222222");
	}

	// ConcurrentHashMap

	public static void test4() {
		IntStream.range(1, 10).filter(i -> i % 2 == 0).findFirst().ifPresent(System.out::println);
		IntStream.range(1, 10).map(i -> i * 2).forEach(System.out::println);
		int value = IntStream.range(1, 10).reduce(0, Integer::sum);
		System.out.println(value);

		List<String> fruits = Arrays.asList(new String[] { "apple", "orange", "pear" });
		int totalLength = fruits.stream().reduce(0, (sum, str) -> sum + str.length(), Integer::sum);
		System.out.println("totalLength = " + totalLength);

		int totalLength2 = fruits.stream().mapToInt(String::length).reduce(0, Integer::sum);
		System.out.println("totalLength2 = " + totalLength2);

		// 字符串首字母大写并连接
		StringBuilder upperCase = fruits.stream().collect(StringBuilder::new,
				(builder, str) -> builder.append(str.substring(0, 1).toUpperCase()), StringBuilder::append);
		System.out.println(upperCase.toString());
	}
}

interface A {
	default void foo() {
		System.out.println("foo");
	}

	static void a1() {
		System.out.println("a1");
	}
}
