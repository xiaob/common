package com.yuan.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.file.DefaultFilenameFilter;

public class CompileUtil {

	private static final Logger log = LoggerFactory.getLogger(CompileUtil.class);
	
	private static List<File> sourceList = new ArrayList<File>(); //源文件列表
	public static Boolean DEBUG = false;
	public static String TARGET;
	public static String SOURCE;
	
	public static void main(String[] args) throws Exception{
		DEBUG = true;
		defaultCompile("d:/tmp", null);
		//System.out.println(getSourceList("d:/tmp"));
	}// main
	
	public static synchronized void defaultCompile(String basePath, String annotationProcessor)throws Exception{
		File f = new File(basePath);
		String sourcePath = f.getAbsolutePath()+File.separator+"src";
		String targetPath = f.getAbsolutePath()+File.separator+"bin";
		String libPath = f.getAbsolutePath()+File.separator+"lib";
		compilePackage(sourcePath,targetPath,libPath,annotationProcessor);
	}
	

	/**
	 * 编译包及其子包
	 * @param sourcePath String
	 * @param targetPath String 编译后的类文件的输出文件夹
	 * @param libPath String 放置第三方类库的文件夹
	 * @throws Exception
	 */
	public static synchronized  void compilePackage(String sourcePath , String targetPath , String libPath, String annotationProcessor)throws Exception{
		sourceList.clear();
		compileSourceFile(getSourceList(sourcePath),targetPath,libPath,annotationProcessor);
	}
	
	private static List<File> getSourceList(String dirPath)throws Exception{
		
		File dir = new File(dirPath);
		File[] files = dir.listFiles(new DefaultFilenameFilter("java"));
		for(File f:files){
			if(f.isDirectory()){//如果是文件夹则迭代 
				getSourceList(dirPath+File.separator+f.getName());
			}else{
				sourceList.add(f);
			}
		}
		return sourceList;
	}
	
	/**
	 * 编译单个Java源文件
	 * @param fileName String
	 * @param targetPath String
	 * @param libPath String
	 */
	public static void compileSourceFile(final String fileName , String targetPath , String libPath, String annotationProcessor){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		try {
			Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects(fileName);
			compiler.getTask(null, fileManager, null, getCompileOptions(targetPath,libPath,annotationProcessor), null, sourcefiles).call();
			fileManager.close();
		}catch(Exception e){
			log.warn(e.getMessage(), e);
		}
	}
	
	/**
	 * @param files List<File>
	 * @param targetPath String 目标文件夹
	 * @param libPath String 放置第三方类库的文件夹
	 */
	private static void compileSourceFile(List<File> files , String targetPath , String libPath, String annotationProcessor){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		try {
			Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjectsFromFiles(files);
			compiler.getTask(null, fileManager, null, getCompileOptions(targetPath,libPath,annotationProcessor), null, sourcefiles).call();
			fileManager.close();
		}catch(Exception e){
			log.warn(e.getMessage(), e);
		}
	}
	
	/**
	 * 设置编译选项
	 * @param targetPath String
	 * @param libPath String
	 * @return
	 */
	private static Iterable<String> getCompileOptions(String targetPath , String libPath, String annotationProcessor){
		List<String> options = new ArrayList<String>();
		
		//设置编译后生成的类文件的输出目录
		if(targetPath != null){
			options.add("-d");
			options.add(targetPath);
		}
		
		//设置用到的第三方类库的目录
		if(libPath != null){
			options.add("-classpath");
			options.add(getLibStr(libPath));
		}
		
		//设置注解处理器
		if(annotationProcessor != null){
			options.add("-processor");
			options.add(annotationProcessor);
		}
		
		if(DEBUG){
			options.add("-g:vars");
		}
		
		if(TARGET != null){
			options.add("-target");
			options.add(TARGET);
		}
		if(SOURCE != null){
			options.add("-source");
			options.add(SOURCE);
		}
		return options;
	}
	
	private static String getLibStr(String libPath){
		StringBuffer sb = new StringBuffer();
		File libDir = new File(libPath);
		if(!libDir.exists()){
			return ".";
		}
		File[] jarFiles = libDir.listFiles(new DefaultFilenameFilter("jar"));
		if(jarFiles.length == 0){
			return ".";
		}
		for(File f:jarFiles){
			if(!f.isDirectory()){
				System.out.println(libPath+File.separator+f.getName());
				sb.append(libPath+File.separator+f.getName()+";"); //多个jar文件用分号隔开
			}
		}
		return sb.substring(0, sb.lastIndexOf(";"));
	}
	
}
