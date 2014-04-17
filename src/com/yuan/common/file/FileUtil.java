package com.yuan.common.file;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.Tools;

public class FileUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static void main(String[] args) throws Exception{
		Files.delete(Paths.get("d:/data"));
		
	}
	public static void writeToFile(String content, String fullPath)throws IOException{
		writeToFile(content,fullPath,false,"UTF-8");
	}
	
	/**
	 * 将一个字符串写入一个文本文件中
	 * @param content String 要写入的字符串
	 * @param fullPath
	 * @param append boolean 是追加还是覆盖，true为追加 
	 * @param encoding String 文本文件的编码
	 * @throws IOException
	 */
	public static void writeToFile(String content, String fullPath, boolean append, String encoding)throws IOException{
		File parent = new File(new File(fullPath).getParent()); //得到父文件夹
		if(!parent.exists()){
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(fullPath).getAbsolutePath(),append);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos,encoding));
		pw.print(content);
		pw.close();
		fos.close();
	}
	
	public static void writeToFile(byte[] data, String fullPath)throws IOException{
		File parent = new File(new File(fullPath).getParent()); //得到父文件夹
		if(!parent.exists()){
			parent.mkdirs();
		}
		
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(fullPath);
			fos.write(data);
		}finally{
			if(fos != null)
				fos.close();
		}
	}
	
	/**
	 * 移动文件或者文件夹,如从e:/aa.txt到e:/tmp/aa.txt, 从e:/aa到e:/bb/aa
	 * @param input String
	 * @param output String
	 */
	public static void move(String input, String output){
	    File inputFile = new File(input);
	    File outputFile = new File(output);
	    try {
	      inputFile.renameTo(outputFile);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	 }//move
	
	/**
	 * 复制单个文件
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(String source, String dest)throws IOException{
		final int BUFSIZE = 65536;
		File f = new File(source);
		File f2 = new File(dest);
		if(!f.exists()){
			return ;
		}
		if(!f2.exists()){
			File parent = new File(f2.getParent()); //得到父文件夹
			if(!parent.exists()){
				parent.mkdirs();
			}
			f2.createNewFile();
		}
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
		byte[] buf = new byte[BUFSIZE];
		int len = 0;
		while((len = bis.read(buf))!=-1){
			bos.write(buf, 0, len);
		}
		bos.close();
		bis.close();
	}
	
	/**
	 * 复制文件夹及其子文件夹
	 * @param source String 源文件夹,如: d:/tmp
	 * @param dest String 目标文件夹,如: e:/tmp
	 * @throws IOException
	 */
	public static void copyFolder(String source, String dest)throws IOException{
		
		File f1 = new File(source);
		File f2 = new File(dest);
		if(!f1.exists()){
			return ;
		}
		if((!f2.exists())&&(f1.isDirectory())){
			f2.mkdirs();
		}
		String[] fileList = f1.list();
		if(fileList == null){
			return ;
		}
		for(String file:fileList){
			String newSource = f1.getAbsolutePath() + File.separator + file;
			String newDest = f2.getAbsolutePath() + File.separator + file;
			if(new File(newSource).isDirectory()){
				logger.info("正在复制文件夹从"+newSource+"到"+newDest+"。。。");
				copyFolder(newSource, newDest);
			}else{
				logger.info("正在复制文件从"+newSource+"到"+newDest+"。。。");
				copyFile(newSource, newDest);
			}
		}//for
	}
	
	/**
	 *  删除某个文件夹下的所有文件和所有子文件夹, 不包括它自己
	 * @param folder String
	 * @param delFolder boolean 是否连文件夹一起删除
	 * @throws IOException
	 */
	public static void deleteAllFiles(String folder, boolean delFolder)throws IOException {
		File f1 = new File(folder);

		if (!f1.exists()) {
			return;
		}
		String[] fileList = f1.list();
		if (fileList == null) { //空文件夹
			if(delFolder){
				logger.info("正在删除文件夹"+f1.getAbsolutePath()+"。。。");
				f1.delete(); //删除文件夹
			}
			return;
		}
		for (String file : fileList) {
			String newSource = f1.getAbsolutePath() + File.separator + file;
			if (new File(newSource).isDirectory()) {
				deleteAllFiles(newSource, delFolder);
				if(delFolder){
					logger.info("正在删除文件夹"+newSource+"。。。");
					new File(newSource).delete(); //删除文件夹
				}
			} else {
				logger.info("正在删除文件"+newSource+"。。。");
				new File(newSource).delete();
			}
		}//for
	}
	
	/**
	 * 删除整个文件夹，包括它本身
	 * @param dir String
	 * @throws IOException
	 */
	public static void deleteFolder(String dir)throws IOException{
		deleteAllFiles(dir, true);
		File f = new File(dir);
		logger.info("正在删除文件夹"+f.getAbsolutePath()+"...");
		f.delete();
	}
	
	public static String readText(Path path) throws IOException{
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		
		StringBuilder sb = new StringBuilder();
		
		for(String line : lines){
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	public static byte[] readFile(URL url) throws IOException, URISyntaxException{
		String protocol = url.getProtocol();
		if(protocol.equals("file")){
			return Files.readAllBytes(Paths.get(url.toURI()));
		}else if(protocol.equals("jar")){
			JarURLConnection conn = null;
			InputStream is = null;
			try{
				conn = (JarURLConnection)url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();
				
				return Tools.readStream(is);
			}finally{
				if(is != null){
					is.close();
				}
			}
		}else if(protocol.equals("http")){
			HttpURLConnection conn = null;
			InputStream is = null;
			try{
				conn = (HttpURLConnection)url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();
				return Tools.readStream(is);
			}finally{
				if(is != null){
					is.close();
				}
				if(conn != null){
					conn.disconnect();
				}
			}
		}else if(protocol.equals("https")){
			HttpsURLConnection conn = null;
			InputStream is = null;
			try{
				conn = (HttpsURLConnection)url.openConnection();
				conn.setDoInput(true);
				is = conn.getInputStream();
				return Tools.readStream(is);
			}finally{
				if(is != null){
					is.close();
				}
				if(conn != null){
					conn.disconnect();
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断一个文件是否为文本文件
	 * 顺序地读出这个文件的每一个字节，如果文件里有一个字节的值等于0，那么这个文件就不是文本文件；
	 * 反之，如果这个文件中没有一个字节的值是0的话，就可以判定这个文件是文本文件了。
	 * @param file
	 *            String 文件名,包括路径
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean isTextFile(String file)throws IOException{
		boolean isText = true;
		FileInputStream fis = new FileInputStream(file);
		int data;
		while((data = fis.read())!=-1){
			if(data == 0){
				isText =  false;
				break;
			}//if
		}//while
		fis.close();
		return isText;
	}
	
	/**
	 * 使用默认程序打开所选文件, 若是bat,exe等可执行文件,则可能执行之
	 * @param fullPath String
	 */
	public static void open(String fullPath) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			File file = new File(fullPath);
			if (file != null) {
				try {
					desktop.open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}//if
		} else {
			System.out.println("不支持desktop");
		}//if

	}//open
	
	public static String formatFileSize(long fileSize) {
		StringBuffer sb = new StringBuffer();
		if (fileSize < 1024) {
			sb.append(fileSize);
			sb.append("B");
		}
		if (1024 * 1024 > fileSize && fileSize >= 1024) {
			String num = String.valueOf((double) (fileSize / 1024));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("KB");
		}
		if (1024 * 1024 * 1024 > fileSize && fileSize >= 1024 * 1024) {
			String num = String.valueOf((double) (fileSize / (1024 * 1024)));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("MB");
		}
		if (1024 * 1024 * 1024 * 1024 >= fileSize && fileSize >= 1024 * 1024 * 1024) {
			String num = String.valueOf((double) (fileSize / (1024 * 1024 * 1024)));
			sb.append(num.substring(0, num.indexOf(".") + 2));
			sb.append("GB");
		}
		return sb.toString();
	}
	
	public static boolean endsWithFileSeparator(String filePath){
		return filePath.endsWith("/") || filePath.endsWith(File.separator);
	}
	
	public static String deleteLastFileSeparator(String filePath){
		if(filePath.endsWith("/")){
			return filePath.substring(0, filePath.length()-1);
		}else if(filePath.endsWith(File.separator)){
			return filePath.substring(0, filePath.lastIndexOf(File.separator));
		}
		return filePath;
	}
	
	/**
	 * 列出子文件夹
	 * @param parentDir File
	 * @return List<File>
	 */
	public static List<File> listDir(File parentDir){
		List<File> dirList = new ArrayList<File>();
		File[] files = parentDir.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				dirList.add(file);
			}
		}
		return dirList;
	}
	
	public static File getFileFromClassPath(Class<?> clazz, String name) throws URISyntaxException{
		
		return new File(clazz.getResource(name).toURI());
	}
}
