package tmp.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestCollection {

	public static void main(String[] args) {
		testCollection();

	}
	public static void testList(){
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		
		try {
			for(String s : list){
				list.remove(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(list);
		System.out.println("=========");
		
		Iterator it = list.iterator();
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		System.out.println(list);
	}
	
	public static void testCollection(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		
		SetList setList = new SetList(10);
		setList.addAll(list);
		System.out.println(setList);
		list.add(3);
		list.add(4);
		setList.addAll(list);
		System.out.println(setList);
	}
	
}

class SetList{
	
	private int[] a;
	private int index = -1;
	private HashMap<Integer, Object> map = new HashMap<Integer, Object>();
	private static final Object PRESENT = new Object();
	
	public SetList(int size){
		a = new int[size];
	}
	
	//O(1)
	public void add(Integer element){
		if(!map.containsKey(element)){
			a[++index] = element;
			map.put(element, PRESENT);
		}
	}
	
	//O(N)
	public void addAll(Collection<Integer> collection){
		for(Integer element : collection){
			add(element);
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<=index; i++){
			sb.append(a[i]);
			if(i != index){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
