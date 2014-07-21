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
//		SshSession w2 = new SshSession(jsch, "10.130.130.73", 22, "root", "123456");
		SshSession w2 = new SshSession(jsch, "210.242.74.137", 22, username, password);
//		w2.cmd("ls /opt");
//		w2.cmd("echo www > /tmp/grep/80000000545809/1fu/t_3.txt");
		w2.cmd("cat /tmp/grep/80000005985380/1fu/protocol* > /tmp/grep/80000005985380/1fu/tmp.txt");
//		w2.cmd("echo rrr >> /tmp/grep/80000000545809/1fu/tmp.txt");
//		w2.cmd("grep '11' /tmp/grep/80000000545809/1fu/*");
//		w2.get("/opt/rsync_log.sh", "d:/tmp/log.sh");
		w2.close();
	}
}
