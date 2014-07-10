/**
 * 
 */
package com.yuan.common.ssh;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

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
		cmd(new File("d:/tmp/grep/t.txt"), "cat", "d:/tmp/grep/t1/*");
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
			Scanner scanner = new Scanner(p.getErrorStream());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		}
		if(p.getInputStream() != null){
			Scanner scanner = new Scanner(p.getInputStream());
			while (scanner.hasNextLine()) {
				out.println(scanner.nextLine());
			}
			scanner.close();
		}
		p.waitFor();
	}
	
}
