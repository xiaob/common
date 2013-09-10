package tmp.others;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.yuan.common.util.CharacterCoder;
import com.yuan.common.util.DateUtil;
import com.yuan.common.util.ReflectUtil;

public class Demo1 {

	public static void main(String[] args)throws Exception {
		
		test5();
	}
	
	public static void test1(){  
		System.out.println(new DecimalFormat(".00").format(7d/2d));
	}
	public static void test2(){
		System.out.println(DateUtil.format(new Date(1315217558000L)));
	}
	public static void test3()throws Exception{
		final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);
		show("size = " + queue.size() + "剩余容量：" + queue.remainingCapacity());
		Thread t = new Thread(){
			public void run(){
				try {
					queue.put("111");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		t.join();
		show("size = " + queue.size() + "剩余容量：" + queue.remainingCapacity());
	}
	public static void show(String message){
		System.out.println(message);
	}
	public static void test4(){
		try {
			Class<?> clazz = Demo1.class.getMethod("test4").getReturnType();
			show(clazz.getName());
			show(clazz.isPrimitive() + "");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	public static void test5(){
		char c1 = 304;
		char c2 = 8490;
		System.out.println(c1);
		System.out.println(c2);
	}
	public static void test6(){
		File file  = new File(new File("d:/test"), "a/index.smil");
		System.out.println(file.getAbsolutePath());
	}
	public static void test7(){
		try {
			System.out.println(DigestUtils.md5Hex("中国中国".getBytes("UTF-8")));
			byte[] buf = new byte[12];
			buf[0] = (byte)0xe4; // "H"
			buf[1] = (byte)0xb8; // "H"
			buf[2] = (byte)0xad; // "H"
			buf[3] = (byte)0xe5; // "H"
			buf[4] = (byte)0x9b; // "H"
			buf[5] = (byte)0xbd; // "H"
			buf[6] = (byte)0xe4; // "H"
			buf[7] = (byte)0xb8; // "H"
			buf[8] = (byte)0xad; // "H"
			buf[9] = (byte)0xe5; // "H"
			buf[10] = (byte)0x9b; // "H"
			buf[11] = (byte)0xbd; // "H"
			System.out.println(DigestUtils.md5Hex(buf));
			String s1 = "dfg2679f-586d-472f-9f08-62fd5c2990e7appKey90c8c0a9-2462-478b-bb76-f5dfc3d566d3channelId0classIdkey黄石公园methodsearch_video_listpage1screen240*320sessionf881e81e-4c6b-4791-bee3-c4908bc2eeeesize6system4.2timestamp20111012174040transactionId5userId0version1.0visionNo1.1.0dfg2679f-586d-472f-9f08-62fd5c2990e7";
			
			System.out.println(DigestUtils.md5Hex(s1.getBytes("UTF-8")));
			
			String messageContent = "中国";
			String e = Base64.encodeBase64String(messageContent.getBytes("UTF-8")).trim();
			System.out.println(e);
			System.out.println(new String(Base64.decodeBase64(e.getBytes("UTF-8")),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public static void test8(){
		String s = "\\u5c0f\\u8d44\\u5973\\u5b69\\u5411\\u524d\\u51b2(\\u90b1\\u6cfd\\/\\u90ed\\u4e66\\u7476)\\uff08\\u7b2c1\\u96c6\\uff09";
		String s1 = "\\u5c0f\\u8d44\\u5973\\u5b69\\u5411\\u524d\\u51b2";
		String s2 = "\\u90b1\\u6cfd";
		String s3 = "\\u90ed\\u4e66\\u7476";
		String s4 = "\\uff08\\u7b2c1\\u96c6\\uff09";
		String ss = CharacterCoder.decodeUnicode(s1) + "(" + CharacterCoder.decodeUnicode(s2) + "/" + CharacterCoder.decodeUnicode(s3) + ")" + CharacterCoder.decodeUnicode(s4);
		System.out.println(ss);
	}
	
	public static void test9(){
		throw new RuntimeException("11111");
	}
	public static void test10(){
		try {
			ReflectUtil.execMethod(new Demo1(), "test9");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void test11(){
		Deque<String> deque = new ArrayDeque<String>();
		deque.push("1");
		deque.push("2");
		deque.push("3");
		printStack(deque);
	}
	
	public static void printStack(Deque<String> deque){
		if(!deque.isEmpty()){
			String s = deque.pop();
			printStack(deque);
			System.out.println(s);
		}
	}
	
	public static void countWordFrequency(String sourceText){
		String[] words = sourceText.split(" ");
		Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for(int i=0; i<words.length; i++){
			Integer wordFrequency = (sourceText.length() - sourceText.replaceAll(words[i], "").length())/words[i].length();
			wordFrequencyMap.put(words[i], wordFrequency);
			System.out.println(words[i] + " : " + wordFrequency);
		}
		
	}
	public static void countWordFrequency2(String sourceText){
		String[] words = sourceText.split(" ");
		Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for(int i=0; i<words.length; i++){
			if(wordFrequencyMap.containsKey(words[i])){
				int cnt = wordFrequencyMap.get(words[i]);
				cnt++;
				wordFrequencyMap.put(words[i], cnt);
			}else{
				wordFrequencyMap.put(words[i], 1);
			}
		}
		for(Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
}
