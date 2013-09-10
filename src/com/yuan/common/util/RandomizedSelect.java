package com.yuan.common.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeSet;
  
/** 
 *  
 * 参考：--------------------------------------------- 
 * http://blog.csdn.net/chen09/article/details/6531678 
 *  
 * 快速选择算法 和 第三名：BFPRT 算法 类似，都是在一个无序的数组中寻找第K小的数， 
 *  
 * 百度百科上解释了中位数的重要性及其意义，上面的2种算法都是属于中位数算法的 
 * 参考：http://baike.baidu.com/view/170892.htm 
 *  
 * 总结：----------------------------------------------- 
 * 这个中位数之中位数算法，是用来查找未排序集合中第K个数的算法。<br> 
 * 主要思想是找到集合中的中间点p，左边的数必须小于中间点位置的数，右边的必须大于它，然后判断找到的中间点是否等于K，<br> 
 *  1、如果等于则找到了<br> 
 *  2、如果小于(k<p),则对0-p范围的数按上面的思想继续进行查找 <br> 
 *  3、如果大于(k>p),则对p-len范围的数按上面的思想继续进行查找，只是不需要找k个数了，而是找p-k个数即可<br> 
 *  
 * 找中间点是快速选择算法的核心思想，为什么要找中间点？因为我们没有必要直到这个集合的顺序是怎么样的，<br> 
 * 更加没有必要去对其进行排序，我们只需要知道某个中间点的左边的数都<br> 
 * 小于该中间点位置的数，反之右边的都大于，如此是递归下去，找到第K大的数只是时间问题.<br> 
 *  
 * 其实关于查找第K个最大(小)的数是算法有好多，这只是其中比较好的一种方法，另一种较好的比如借助于大小堆也不错，充分体现了堆的强大，<br> 
 * 记得有个面试题是：1亿个数据中找前100个数。就是利用大小堆完成的效率比较高.<br> 
 * ------------------------------------------------ 
 */  
public class RandomizedSelect {  
      
    private static <T> int partition(T[] a, Comparator<? super T> c, int p, int r) {  
        T t = a[r - 1];  
        int i = p - 1;// 中间点，小的放在i的左边，大的放右边，最后返回的i就是中间点  
        for (int j = p; j < r - 1; j++) {// 从p到r-2，为什么是r-2呢？因为第r-1位置的数已被做为比较的对象了  
            if (c.compare(a[j], t) <= 0) {// 从左边开始，循环的拿左边的数和最后一个数进行比较,把小的放在左边大的放右边，并且计数中位数  
                i++;  
                swap(a, i, j);  
            }  
        }  
        // 在randomizedPartition方法中我们把主元放到了最后，那么中间点找到后我们得把主元放到中间点来，那么i+1便是最后得到的中间点  
        swap(a, i + 1, r - 1);  
        return i + 1;  
    }  
  
    private static <T> int randomizedPartition(T[] a, Comparator<? super T> c,  
            int p, int r) {  
        int i = new Random().nextInt(r - p) + p;// 随机选择算法  
        // 随机出来的位置的数做为主元，所谓主元就是被比较的对象，我们假设有一个数的大小处于p到r的中间，这样的数被称为主元  
        // 这个主元要被放到p...r的最后位置，所以这里要和最后一个元素交换  
        swap(a, i, r - 1);  
        return partition(a, c, p, r);  
    }  
  
    private static <T> void swap(T[] a, int i, int j) {  
        T t = a[i];  
        a[i] = a[j];  
        a[j] = t;  
    }  
  
    private static <T> T randomizedSelect(T[] t,  
            Comparator<? super T> comparator, int p, int r, int i) {  
        if (p == r)// 找到第K个数  
            return t[p];  
        int q = randomizedPartition(t, comparator, p, r);// 找到中间点  
        int k = q - p + 1;// 中间点q前面有有多少个数字  
        if (i <= k)// 判断是否找到第i个数  
            return randomizedSelect(t, comparator, p, q, i);// 区间查找  
        else  
            return randomizedSelect(t, comparator, q + 1, r, i - k);// 区间查找  
    }  
  
    public static <T> T randomizedSelect(T[] t,  
            Comparator<? super T> comparator, int i) {  
        return randomizedSelect(t, comparator, 0, t.length, i);  
    }  
    
    public static void test1(){
    	 Integer[] ints = new Integer[20];  
         Random ran = new Random();  
         int k = 10;  
         for (int i = 0; i < ints.length; i++) {  
             ints[i] = ran.nextInt(100);  
         }  
         Integer positiong = randomizedSelect(ints, new Comparator<Integer>() {  
             public int compare(Integer o1, Integer o2) {  
                 return o1.intValue() - o2.intValue();  
             }  
         }, k);  
         System.out.println("快速选择算法求出的,第"+k+"个最大数是："+positiong);  
         Arrays.sort(ints);  
         System.out.println("排序后，第"+k+"个最大数是："+ints[k-1]);  
         System.out.println(Arrays.toString(ints));  
    }
  
    public static void main(String[] args) {  
    	test2();
    }  
    private static void test2(){
    	int[] ints = new int[20];  
        Random ran = new Random();  
        for (int i = 0; i < ints.length; i++) {  
            ints[i] = ran.nextInt(100);  
        }  
        System.out.println(ints);
        treeTop(ints, 5);
        heapTop(ints, 5);
        System.out.println(maxSubSum(ints));
    }
    
    public static void treeTop(int[] arr, int topNumber){  
        TreeSet<Integer> tree = new TreeSet<Integer>();  
          
        for (int i = 0; i < arr.length; i++) {  
            if (tree.size() < topNumber) {  
                tree.add(arr[i]);  
            } else if (tree.first() < arr[i]) {  
                tree.remove(tree.first());  
                tree.add(arr[i]);  
            }  
        }  
        System.out.println(tree);  
    } 
    //改成最小堆后，经测试，最小堆花费1800毫秒左右的时间，TreeSet花费的时间大概3600毫秒，接近2倍的差距
    public static void heapTop(int[] arr, int topNumber){  
    	PriorityQueue<Integer> heap = new PriorityQueue<Integer>(topNumber);   
    	
    	for (int i = 0; i < arr.length; i++) {  
            if (heap.size() < topNumber) {  
                heap.add(arr[i]);  
            } else if (heap.peek() < arr[i]) {  
                heap.poll();  
                heap.add(arr[i]);  
            }  
        } 
    	System.out.println(heap);  
    } 
    //最大子序列
    public static int maxSubSum(int[] a){
    	int maxSum = 0, thisSum = 0;
    	for(int j=0; j<a.length; j++){
    		thisSum += a[j];
    		if(thisSum > maxSum){
    			maxSum = thisSum;
    		}else if(thisSum < 0){
    			thisSum = 0;
    		}
    	}
    	return maxSum;
    }
}  
