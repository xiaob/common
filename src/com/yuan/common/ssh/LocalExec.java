/**
 * 
 */
package com.yuan.common.ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * 本地命令运行器
 * @author <a href="mailto:cihang.yuan@happyelements.com">cihang.yuan</a>
 * @version 1.0 2014年7月10日
 * @since 1.6
 */
public class LocalExec {
	
	public static void main(String args[])throws Exception{
//		cmd("mkdir", "d:/tmp/grep/t1");
//		cmd(new File("d:/tmp/grep/t.txt"), "dir", "d:/tmp/grep");
//		 cmd("rm", "-rf", "d:/tmp/grep/t1");
//		cmd(new File("d:/tmp/grep/80000005985380/1fu/tmp.txt"), "cat", "d:/tmp/grep/80000005985380/1fu/t1.txt");
		mergeFile("d:/tmp/grep/80000005985380/1fu", "d:/tmp/grep/80000005985380/1fu/tmp.txt");
	}

	public static void cmd(String... cmd) throws InterruptedException, IOException{
		cmd(System.out, cmd);
	}
	
	public static void cmd(File outFile, String... cmd) throws InterruptedException, IOException{
		PrintStream stream = null;
		try {
			stream = new PrintStream(outFile);
			cmd(stream, cmd);
		} finally {
			if(stream != null) stream.close();
		}
		
	}
	public static void cmd(PrintStream out, String... cmd) throws InterruptedException, IOException{
		ProcessBuilder pb = new ProcessBuilder(cmd);
		
		Process p  = pb.start();
		if(p.getErrorStream() != null){
			printStream(p.getErrorStream(), null);
		}
		if(p.getInputStream() != null){
			printStream(p.getInputStream(), out);
		}
		p.waitFor();
	}
	private static void printStream(InputStream in, PrintStream out) throws IOException{
		if(out == null){
			out = System.out;
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				out.println(line);
			}
		} finally {
			if(br != null) br.close();
		}
	}
	
	public static void mergeFile(String sourceDir, String dest) throws IOException{
		File sourceFile = new File(sourceDir);
		File[] fs = sourceFile.listFiles();
		PrintStream out = null;
		try {
			out = new PrintStream(new File(dest));
			for(File f : fs){
				System.out.println("merge file " + f.getAbsolutePath() + " > " + dest);
				mergeFile(f, out);
			}
		} finally {
			if(out != null) out.close();
		}
	}
	private static void mergeFile(File sourceFile, PrintStream out) throws IOException{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(sourceFile);
			printStream(fis, out);
		} finally {
			if(fis != null) fis.close();
		}
	}
	
}
