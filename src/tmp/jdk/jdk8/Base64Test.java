package tmp.jdk.jdk8;

import java.util.Arrays;
import java.util.Base64;

public class Base64Test {

	public static void main(String[] args) {
		test2();

	}

	public static void test1() {
		System.out.println(Base64.getEncoder().encodeToString("1111".getBytes()));
		System.out.println(Base64.getMimeEncoder().encodeToString("1111".getBytes()));
		System.out.println(Base64.getUrlEncoder().encodeToString("http://localhost:8080/".getBytes()));
	}

	public static void test2() {
		Arrays.stream(Base64Test.class.getMethods()).forEach((method) -> {
			System.out.println(method.getName());
			Arrays.stream(method.getParameters()).forEach((parameter) -> {
				System.out.println("====" + parameter.isNamePresent() + ", " + parameter.getName());
			});
		});
	}

}
