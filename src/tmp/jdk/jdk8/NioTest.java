package tmp.jdk.jdk8;

import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NioTest {

	public static void main(String[] args) throws Exception {
		// test1();
		// test2();
//		test3();
		test4();
	}

	public static void test1() throws Exception {
		String file = "d:/tmp/a.txt";
		Files.newBufferedReader(Paths.get(file)).lines().forEach(System.out::println);

		Files.list(Paths.get("d:/")).forEach(System.out::println);

		System.out.println("==========");
		Files.walk(Paths.get("d:/tmp"), FileVisitOption.FOLLOW_LINKS).forEach(System.out::println);
		System.out.println("==========");

		long c = Files.find(Paths.get("d:/tmp"), 3, (path, basicFileAttributes) -> {
			// System.out.println(path.getFileName());
				return path.getFileName().toString().equals("a.txt");
			}, FileVisitOption.FOLLOW_LINKS).count();
		System.out.println(c);

		Files.lines(Paths.get("d:/tmp/a.txt")).forEach(System.out::println);
	}

	public static void test2() {
		AtomicInteger nextId = new AtomicInteger(0);

		ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> {
			return nextId.getAndIncrement();
		});

		System.out.println(threadLocal.get());
	}

	public static void test3() {
		String message = String.join("-", "Java", "is", "cool");
		System.out.println(message);
		System.out.println(Long.sum(10, 20));

		Boolean b1 = true;
		Boolean b2 = false;
		System.out.println(Boolean.logicalAnd(b1, b2));
		System.out.println(Boolean.logicalOr(b1, b2));
		System.out.println(Boolean.logicalXor(b1, b2));
		Optional<Integer> optional = Optional.ofNullable(null);
		System.out.println(optional.isPresent());

		StringJoiner sj = new StringJoiner(":", "[", "]");
		sj.add("George").add("Sally").add("Fred");
		String desiredString = sj.toString();
		System.out.println(desiredString);

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
		String commaSeparatedNumbers = numbers.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
		System.out.println(commaSeparatedNumbers);
		
//		Arrays.setall
	}
	
	public static void test4(){
		System.out.println(Objects.isNull(null));
	}

}
