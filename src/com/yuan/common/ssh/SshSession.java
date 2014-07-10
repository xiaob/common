/**
 * 
 */
package com.yuan.common.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

/**
 * @version 1.0 2014年7月2日
 * @since 1.6
 */
public class SshSession {

	private Session session;
	
	public SshSession(JSch jsch, String host, int port, String username, String password) throws JSchException{
		session = jsch.getSession(username, host, port);
		
		session.setPassword(password);
		session.connect();
	}
	
	/**
	 * 在远程主机上执行命令
	 * @param command
	 * @throws JSchException
	 * @throws IOException
	 */
	public void cmd(String command) throws JSchException, IOException{
		ChannelExec channel = (ChannelExec)session.openChannel("exec");
		
		channel.setCommand(command);
		channel.setInputStream(null);
		channel.setErrStream(System.err);
		channel.connect();
		
		printStream(channel.getInputStream());
		
		channel.disconnect();
	}
	private void printStream(InputStream in){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将远程文件复制到本地
	 * @param remoteDir
	 * @param localDir
	 * @throws JSchException
	 * @throws IOException
	 * @throws SftpException 
	 */
	public void get(String remoteFile, String localFile) throws JSchException, SftpException{
		ChannelSftp channel = (ChannelSftp)session.openChannel("sftp");  
		channel.connect(); 
		
		channel.get(remoteFile, localFile, new SftpProgressMonitor(){

			@Override
			public void init(int op, String src, String dest, long max) {
				System.out.println(op + "," + src + "," + dest + "," + max);
			}

			@Override
			public boolean count(long count) {
				System.out.println(count);
				return true;
			}

			@Override
			public void end() {
				System.out.println("finish!");
			}});
		
		channel.disconnect();
	}
	
	public void close(){
		if(session != null){
			session.disconnect();
		}
	}
}
