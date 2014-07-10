/**
 * 
 */
package com.yuan.common.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

/**
 * @author <a href="mailto:cihang.yuan@happyelements.com">cihang.yuan</a>
 * @version 1.0 2014年7月2日
 * @since 1.6
 */
public class SshFactory {

	private static JSch jsch;
	private static final String username = "cihang.yuan";
	private static final String password = "1234qwer!";
	
	static{
		jsch = new JSch();
		JSch.setLogger(new JschLogger());
		JSch.setConfig("StrictHostKeyChecking", "no");  
	}
	
	public static SshSession openSession(String host, int port) throws JSchException{
		return new SshSession(jsch, host, port, username, password);
	}
	
	public static void main(String[] args)throws Exception{
		SshSession w2 = SshFactory.openSession("210.242.242.66", 22);
//		SshSession w3 = SshFactory.openSession("210.242.242.67", 22);
//		SshSession w5 = SshFactory.openSession("210.242.242.61", 22);
//		w2.cmd("ls /opt");
		System.out.println("=============================");
		w2.get("/opt/rsync_log.sh", "d:/tmp/log.sh");
//		w3.cmd("ls");
//		w5.cmd("ls");
		w2.close();
//		w3.close();
//		w5.close();
	}
}
