package com.yuan.common.mysql;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yuan.common.async.AsyncService;
import com.yuan.common.compress.ZipEntryOutputStream;
import com.yuan.common.compress.ZipTool;
import com.yuan.common.file.FileUtil;
import com.yuan.common.util.DateUtil;



public class MysqlTool {
	private static final Logger logger = LogManager.getLogger(MysqlTool.class);
	private final AsyncService service = new AsyncService(1);
	private long size = 0L;
	private String host;
	private Integer port = 3306;
	private String dbName;
	private String user;
	private String password;
	
	static{
//		SystemTool.appendBinPath(MysqlBackup.class);
//		Log4j.load(MysqlTool.class);
	}
	public MysqlTool(String host, String dbName, String user, String password){
		this(host, 3306, dbName, user, password);
	}
	public MysqlTool(String host, Integer port, String dbName, String user, String password){
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.user = user;
		this.password = password;
	}
	
	public synchronized void shutdown(){
		service.shutdown();
	}
	
	public static void notepad(String fileName)throws IOException{
		List<String> commandList = new ArrayList<String>();
		commandList.add("notepad.exe");
		commandList.add(fileName);
		ProcessBuilder proc = new ProcessBuilder(commandList);
		proc.start();
	}
	
	public static void main(String[] args)throws InterruptedException {
		/*
		 * 备份和导入是一个互逆的过程。 备份：程序调用mysql的备份命令，读出控制台输入流信息，写入.sql文件；
		 * 导入：程序调用mysql的导入命令，把从.sql文件中读出的信息写入控制台的输出流 注意：此时定向符">"和" <"是不能用的
		 */
//		 MysqlTool mysqlTool = new MysqlTool("192.168.5.29", "zyc", "root", "123456");
//		 mysqlTool.backup("d:", null);
//		 mysqlTool.shutdown();
		String fn = new File("d:/test/openmas20100203131843.zip").getName();
		System.out.println(fn.substring(0, fn.indexOf(".")) + ".sql");
	}
	
	public synchronized File backup(String dir, StatusListener listener)throws InterruptedException {
		try {
			String timestamp = DateUtil.format(new Date(), "yyyyMMddHHmmss");
			if(!new File(dir).exists()){
				new File(dir).mkdirs();
			}
			dir = FileUtil.deleteLastFileSeparator(dir);

			String sqlFileName = dbName + timestamp + ".sql";
			String sqlFile = dir + File.separator + sqlFileName;
			String zipFileName = dbName + timestamp + ".zip";
			String zipFile = dir + File.separator + zipFileName;
			logger.info(sqlFile);
			
			List<String> commandList = new ArrayList<String>();
			commandList.add("mysqldump");
			commandList.add("--single-transcation");
			commandList.add("--default-character-set=utf8");
			commandList.add("-h" + host);
			commandList.add("-P" + port);
			commandList.add("-u" + user);
			commandList.add("-p" + password);
			commandList.add(dbName);
			if(listener != null){
				listener.started(zipFileName);
			}
			ProcessBuilder proc = new ProcessBuilder(commandList);
			proc.redirectErrorStream(true);
			Process process = proc.start();
			final int BUFSIZE = 1024 * 1024;
//			BufferedInputStream bis = new BufferedInputStream(process.getInputStream(), BUFSIZE);
			InputStream bis = process.getInputStream();
			logger.info("bis = " + bis);
//			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(sqlFile), BUFSIZE);
			BufferedOutputStream bos = new BufferedOutputStream(new ZipEntryOutputStream(zipFile, sqlFileName, "UTF-8"), BUFSIZE);
			int data = -1;
			size = 0L;
			while((data = bis.read()) != -1){
				bos.write(data);
				size ++ ;
				if((size%(1024) == 0) && (listener != null)){
					service.asyncMethod(null, listener, "status", size);
				}
			}
			service.asyncMethod(null, listener, "status", size);
			bos.flush();
			bos.close();
			bis.close();
			int exitValue = process.waitFor();
			if(listener != null){
				service.asyncMethod(null, listener, "finished", size);
			}
			logger.info("exitValue = " + exitValue);
			logger.info("/* backup OK! */");
			return new File(zipFile);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return null;
	}

	public synchronized void restore(String sqlFile, StatusListener listener) {
		try {
			logger.info("正在从文件" + sqlFile + "恢复数据库 ... ...");
			String fileName = new File(sqlFile).getName();
			String entryName = fileName.substring(0, fileName.indexOf(".")) + ".sql";
			List<String> commandList = new ArrayList<String>();
			commandList.add("mysql");
			// 字符集参数必须设置正确, 否则会有异常：java.io.IOException: 管道已结束
			commandList.add("--default-character-set=utf8");
			commandList.add("-h" + host);
			commandList.add("-P" + port);
			commandList.add("-u" + user);
			commandList.add("-p" + password);
			commandList.add(dbName);
			if(listener != null){
				listener.started(fileName);
			}
			ProcessBuilder proc = new ProcessBuilder(commandList);
			Process process = proc.start();
			final int BUFSIZE = 1024 * 1024;
			OutputStream out = process.getOutputStream();// 控制台的输入信息作为输出流
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sqlFile), BUFSIZE);
			BufferedInputStream bis = new BufferedInputStream(ZipTool.readEntry(sqlFile, entryName), BUFSIZE);
			int data = -1;
			size = 0L;
			while((data = bis.read()) != -1){
				out.write(data);
				size ++ ;
				if((size%(1024) == 0) && (listener != null)){
					service.asyncMethod(null, listener, "status", size);
				}
			}
			service.asyncMethod(null, listener, "status", size);
			out.flush();
			bis.close();
			out.close();
			int exitValue = process.waitFor();
			if(listener != null){
				service.asyncMethod(null, listener, "finished", size);
			}
			logger.info("exitValue = " + exitValue);
			logger.info("/* restore OK! */");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

	}
}
