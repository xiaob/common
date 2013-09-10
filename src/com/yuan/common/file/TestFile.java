package com.yuan.common.file;

import java.io.File;
import java.util.List;

public class TestFile {

	public static void main(String[] args) throws Exception{
		test2();
	}
	
	public static void demo1(){
		List<File> fielList = FileUtil.listDir(new File("e:/"));
		for(File file : fielList){
			System.out.println(file.getAbsolutePath());
		}
	}
	
	public static void test2()throws Exception{
//		System.out.println("== " + FileSystems.getDefault().getPath("").toAbsolutePath());
//		System.out.println("== " + FileSystems.getDefault().getPath("/").toAbsolutePath());
//		
//		Path path = FileSystems.getDefault().getPath("bin/resources", "user.json");
//		System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
//		System.out.println(path.toString());
//		System.out.println(path.toAbsolutePath().toString());
//		
//	    List<String> list = Files.readAllLines(path.toAbsolutePath(), StandardCharsets.UTF_8);
//	    for(String line : list){
//			System.out.println(line);
//	    }
	}

}
