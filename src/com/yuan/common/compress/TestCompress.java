package com.yuan.common.compress;

import java.net.URL;

import com.yuan.common.file.FileUtil;
import com.yuan.common.log.Slf4j;

public class TestCompress {

	static{
		Slf4j.load(TestCompress.class);
	}
	public static void main(String[] args)throws Exception {
//		uncompress();
		test1();
//		test2();
	}
	
	public static void test()throws Exception{
		ZipOutputStream zos = new ZipOutputStream("d:test/压缩测试.zip", "UTF-8"); 
		
		ZipEntryOutputStream zeos = new ZipEntryOutputStream(zos, "测试文件1.txt");
		zeos.write("测试1".getBytes("UTF-8"));
		zeos.closeEntry();
		zeos.close();
		
		ZipEntryOutputStream zeos2 = new ZipEntryOutputStream(zos, "测试文件2.txt");
		zeos2.write("测试2".getBytes("UTF-8"));
		zeos2.closeEntry();
		zeos2.close();
		zos.close();
	}
	public static void uncompress()throws Exception{
		JarTool.uncompress("E:\\dev\\workspace\\yuan\\common\\target\\yuan-common-0.1.0.jar");
	}
	
	public static void test1()throws Exception{
		URL url = new URL("jar:file:/E:/struts2-rest-plugin-2.2.1.jar!/struts-plugin.xml");
		System.out.println(new String(FileUtil.readFile(url), "UTF-8"));
	}
	
	public static void test2()throws Exception{
		System.out.println(new URL("file:/E:/dev/workspace/hxbos/src/trunk/modules/core/apif/target/classes/logback.xml").getProtocol());
		System.out.println(new URL("file:/E:/dev/workspace/hxbos/src/trunk/modules/core/apif/target/classes/logback.xml").toURI());
		System.out.println(new URL("jar:file:/E:/struts2-rest-plugin-2.2.1.jar!/struts-plugin.xml").getProtocol());
		System.out.println(new URL("http://java.sun.com/index.html").getProtocol());
		System.out.println(new URL("https://java.sun.com/index.html").getProtocol());
	}

}
