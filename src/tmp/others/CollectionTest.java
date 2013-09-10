package tmp.others;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

import com.sun.tools.jdi.LinkedHashMap;


public class CollectionTest {
	
	public static void main(String[] args) throws IOException {
		testExchanger();
	}   
	
	public static void testCollections(){
		List<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		List<Integer> l2 = Collections.unmodifiableList(l1);
		System.out.println(l2);
//		l2.add(2);
		
		List<Integer> l3 = Collections.singletonList(3);
		l3.add(4);
		
		List<Integer> l4 = Collections.emptyList();
		List<Integer> l5 = new ArrayList<Integer>(Collections.nCopies(5, 0));
		System.out.println(l4);
		System.out.println(l5);
	}
	
	public static void testArrays(){
		int[] a = new int[5];
		Arrays.fill(a, 11);
		System.out.println(Arrays.toString(a));
		Arrays.fill(a, 22);
		System.out.println(Arrays.toString(a));
		
		int[] b = {12,3,34,22,-1};
		Arrays.sort(b);
		System.out.println(Arrays.toString(b));
		
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		Collections.reverse(list);
		System.out.println(list);
	}
	
	public static void testSet(){
		BitSet bitSet = new BitSet(8);
		bitSet.set(2);
		bitSet.set(4);
		show(bitSet);
		show(bitSet.get(0));
		show(bitSet.get(2));
		
		HashSet<Integer> hashSet = new HashSet<Integer>();
		hashSet.add(1);
		show(hashSet);
		
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		treeSet.add(1);
		treeSet.add(2);
		treeSet.add(3);
		treeSet.add(4);
		treeSet.add(5);
		Iterator<Integer> it = treeSet.iterator();
		while(it.hasNext()){
			show2(it.next());
		}
		show(treeSet.first() + ", " + treeSet.last());
		show(treeSet.headSet(3, true));
		show(treeSet.tailSet(3, true));
		show(treeSet.subSet(2, true, 5, true));
		
		LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<Integer>();
		linkedHashSet.add(12);
		linkedHashSet.add(15);
		linkedHashSet.add(14);
		Iterator<Integer> it2 = linkedHashSet.iterator();
		while(it2.hasNext()){
			show2(it2.next());
		}
		
		EnumSet<Color> enumSet = EnumSet.allOf(Color.class);
		enumSet.remove(Color.RED);
		enumSet.add(Color.RED);
		show(enumSet);
	}
	
	public static void testList(){
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		show(list);
	}
	
	public static void testMap(){
		ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
		map.put(1, 1);
		show(map.putIfAbsent(1, 2));
		show(map);
		
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
		treeMap.put(1, 1);
		treeMap.put(2, 2);
		treeMap.put(3, 3);
		treeMap.put(4, 4);
		treeMap.put(5, 5);
		show(treeMap.firstKey() + ", " + treeMap.lastKey());
		show(treeMap.subMap(2, 5));
		
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put(2, 2);
		linkedHashMap.put(3, 3);
		linkedHashMap.put(4, 4);
		show(linkedHashMap);
		
		EnumMap<Color, Integer> enumMap = new EnumMap<Color, Integer>(Color.class);
		enumMap.put(Color.RED, 2);
		show(enumMap);
		
		IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<String, Integer>();
		String i = new String("1");
		String j = new String("1");
		identityHashMap.put(i, 1);
		identityHashMap.put(j, 1);
		show(identityHashMap);
		
		WeakHashMap<Integer, Integer> weakHashMap = new WeakHashMap<Integer, Integer>();
		weakHashMap.put(1, 1);
		show(weakHashMap);
	}
	
	public static void testQueue(){
		ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<Integer>();
		concurrentLinkedQueue.offer(1);
		concurrentLinkedQueue.offer(2);
		show(concurrentLinkedQueue);
		
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
		priorityQueue.offer(3);
		priorityQueue.offer(1);
		priorityQueue.offer(2);
		Integer i = null;
		while((i = priorityQueue.poll()) != null){
			show2(i);
		}
		
		PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<Integer>();
		priorityBlockingQueue.put(1);
		try {
			show(priorityBlockingQueue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDeque(){
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>(); 
		arrayDeque.push(1);
		arrayDeque.offer(2);
		arrayDeque.offerLast(3);
		show(arrayDeque);
		
		LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<Integer>();
		try {
			linkedBlockingDeque.putLast(1);
			show(linkedBlockingDeque.takeFirst());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void testLatch(){
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		new Thread(){
			public void run(){
				show("1111111");
				countDownLatch.countDown();
			}
		}.start();
		
		try {
			countDownLatch.await();
			show("22222");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void testSemaphore(){
		final MyBound myBound = new MyBound(1);
		new Thread(){
			public void run(){
				show("1111111");
				try {
					myBound.take();
					show("2222222");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	private static class MyBound{
		private final Semaphore takeSemaphore;
		private final Semaphore putSemaphore;
		public MyBound(int bound){
			takeSemaphore = new Semaphore(bound);
			putSemaphore = new Semaphore(bound);
		}
		public void take() throws InterruptedException{
			takeSemaphore.acquire();
			
			show(Thread.currentThread().getName() + " take");
			putSemaphore.release();
		}
//		public void put() throws InterruptedException{
//			putSemaphore.acquire();
//			show(Thread.currentThread().getName() + "put");
//			takeSemaphore.release();
//		}
	}
	
	public static void testBarrier(){
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
			public void run() {
				show("=============");
			}
		});
		
		new Thread(){
			public void run(){
				show("1111111");
				try {
					cyclicBarrier.await();
					show("22222222");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				show("333333333");
				try {
					cyclicBarrier.await();
					show("4444444444");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
//		cyclicBarrier.reset(); //可重复使用
	}
	
	public static void testExchanger(){
		final Exchanger<Integer> exchanger = new Exchanger<Integer>(); 
		new Thread(){
			public void run(){
				try {
					show("1111  " + exchanger.exchange(1));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				try {
					show("2222  " + exchanger.exchange(2));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private static void show(Object message){
		System.out.println(message);
	}
	private static void show2(Object message){
		System.out.print(message);
		System.out.print(",");
	}

}

enum Color{
	RED,GREEN
}
