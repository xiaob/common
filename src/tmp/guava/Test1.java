package tmp.guava;

import java.lang.reflect.Method;

import com.google.common.base.Function;

public class Test1 {

	public static void main(String[] args) throws Exception{
		test1();
	}
	
	public static void test1() throws NoSuchMethodException, SecurityException{
		Function<Method, String> function = new Function<Method, String>() {
			@Override
			public String apply(Method method) {
				return method.getName();
			}
		};
		
		String name = function.apply(Test1.class.getDeclaredMethod("test1"));
		System.out.println(name);
	}

}
