package tmp.jdk.jdk7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

	public static void main(String[] args) {
//		testBinary();
//		testNumber();
//		testSwitch();
//		testTry();
		testFile();
	}

	// 二进制字面量
	public static void testBinary(){
		byte b = 0b0001;
		System.out.println(b);
		short s = 0b0010;
		System.out.println(s);
		int i = 0b0011;
		System.out.println(i);
		long l = 0b0100;
		System.out.println(l);
	}
	
	// 数字字面量可以出现下划线
	public static void testNumber(){
		int a = 10_0000_0000;
		long b = 0xffff_ffff_ffff_ffffL;
		byte c = 0b0001_1000;
		System.out.println(a + ", " + b + ", " + c);
	}
	
	// switch支持字符串
	public static void testSwitch(){
		String str = "two";
		switch (str) {
		case "one":
			System.out.println("1");
			break;
		case "two":
			System.out.println("2");
			break;
		default:
			System.out.println("err");
		}
	}
	
	// try-with-resource
	public static void testTry(){
		try {
			new My();
		} catch (Exception e) {
			
		}
		
		try{
			new My().close();;
		}catch(IOException|SQLException e){
			
		}catch (Exception e) {
			
		}
	}
	
	// 文件操作
	public static void testFile(){
		try {
			List<String> list = new ArrayList<String>();
			list.add("111");
			Path path = Paths.get("d:/tmp/a.txt");
			if(Files.notExists(path.getParent())){
				Files.createDirectories(path.getParent());
			}
			Files.write(path, list);
			
//			System.out.println(Files.readAllBytes(Paths.get("")));
			System.out.println(Files.readAllLines(Paths.get("d:/tmp/a.txt")));
//			Files.write(Paths.get(""), bytes, StandardOpenOption.CREATE);
			
			Path otherPath = path.getRoot().resolve("download");
			System.out.println(Files.notExists(otherPath));
			System.out.println(otherPath);
			System.out.println(otherPath.toAbsolutePath());
			System.out.println(otherPath.toRealPath());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

class My implements AutoCloseable{

	@Override
	public void close() throws Exception {
		System.out.println("AutoCloseable");
	}
	
}