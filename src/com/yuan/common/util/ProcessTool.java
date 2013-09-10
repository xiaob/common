package com.yuan.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTool {

	private static final Logger log = LoggerFactory.getLogger(ProcessTool.class);
	
	public static int exec(String cmdText, StringBuilder result, String charset) throws UnsupportedEncodingException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int exitValue = exec(cmdText, baos);
		result.append(new String(baos.toByteArray(), charset));
		
		return exitValue;
	}
	
	public static int exec(String cmdText, OutputStream os){
		String[] cmds = cmdText.split(" ");
		
		ProcessBuilder proc = new ProcessBuilder(Arrays.asList(cmds));
		proc.redirectErrorStream(true);
		int exitValue = 0;
		try {
			Process process = proc.start();
			if(os != null){
				if(process.getInputStream() != null){
					readStream(process.getInputStream(), os);
				}
			}
			exitValue = process.waitFor();
		}catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		
		return exitValue;
	}
	
	private static void readStream(InputStream is, OutputStream os) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(is);
		int data = 0;
		while((data = bis.read()) != -1){
			os.write(data);
		}
	}
	
	public static String getCommandResult(String cmdText, String charset) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		exec(cmdText, sb, charset);
		return sb.toString();
	}
	
	public static void main(String[] args)throws Exception{
//		System.out.println(getCommandResult("ipconfig /all", "GBK"));
//		System.out.println(getCommandResult("netstat -a", "GBK"));
		exec("netstat -a", System.out);
	}
	
}
