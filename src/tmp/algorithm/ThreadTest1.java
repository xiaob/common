package tmp.algorithm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.yuan.common.util.ReflectUtil;


public class ThreadTest1<T> {

	private List<String> list = new ArrayList<String>();
	private List<List<Integer>> list2 = new ArrayList<List<Integer>>(); //多级泛型
	private List<Float[]> list3 = new ArrayList<Float[]>(); //泛型数组
	
	public List<Integer> t1(List<Boolean> list){
		return null;
	}
	
	public static void main(String[] args)throws Exception {
		test3();
	}
	
	//条件编译
	public static void test1(){
		final boolean DEBUG = false;
		if(DEBUG){
			System.out.println("11111");
		}else{
			System.out.println("22222");
		}
	}
	
	public static void test2()throws Exception{
		Field field = ThreadTest1.class.getDeclaredField("list"); //myList的类型是List
		List<Class<?>> list = ReflectUtil.getGenericFieldType(field);
		for(Class clazz : list){
			System.out.println(clazz.getName());
		}
		
		Method method = ThreadTest1.class.getDeclaredMethod("t1", List.class);
		List<Class<?>> returnTypeList = ReflectUtil.getGenericReturnType(method);
		for(Class clazz : returnTypeList){
			System.out.println(clazz.getName());
		}
		List<Class<?>> paramTypeList = ReflectUtil.getGenericParamType(method, 0);
		for(Class clazz : paramTypeList){
			System.out.println(clazz.getName());
		}
		
		List<Class<?>> objTypeList = ReflectUtil.getGenericParameterTypes(new My1());
		for(Class clazz : objTypeList){
			System.out.println(clazz.getName());
		}
	}
	public static void test3()throws Exception{
		Field field = ThreadTest1.class.getDeclaredField("list2"); //myList的类型是List
		List<Class<?>> list = ReflectUtil.getGenericFieldType(field);
		for(Class clazz : list){
			System.out.println(clazz.getName());
		}
		Field field3 = ThreadTest1.class.getDeclaredField("list3"); //myList的类型是List
		List<Class<?>> list3 = ReflectUtil.getGenericFieldType(field3);
		for(Class clazz : list3){
			System.out.println(clazz.getName() + ", " + clazz.getComponentType().getName());
		}

	}

}

class My1 extends ThreadTest1<Double>{
	
}

