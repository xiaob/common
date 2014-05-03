package tmp.jdk.jdk7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MHTest {

	public static void main(String[] args) throws Throwable {
		test1();
		System.out.println(Void.class.getName());
		System.out.println(void.class.getName());
	}

	public static void test1() throws Throwable {
		MethodHandle mh = MethodHandles.lookup().findVirtual(String.class, "replace",
				MethodType.methodType(String.class, CharSequence.class, CharSequence.class));
		Object s = (String)mh.invoke("daddy","d", "n");
		System.out.println(s);
	}

}
