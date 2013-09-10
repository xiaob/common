package com.yuan.common.ntp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.net.daytime.DaytimeTCPClient;
import org.apache.commons.net.daytime.DaytimeUDPClient;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.time.TimeTCPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetTool {

	private static final Logger log = LoggerFactory.getLogger(NetTool.class);
	
	public static void main(String[] args)throws Exception {
		ntp("210.72.145.44", 123);
//		daytime("210.72.145.44");
//		daytimeUDP("210.72.145.44");
//		time("time-a.nist.gov");
		System.out.println("====================");
	}

	/**
	 * RFC 1305: NTP协议全称网络时间协议（Network Time Procotol）它的目的是在国际互联网上传递统一、标准的时间。
	 * 具体的实现方案是在网络上指定若干时钟源网站，为用户提供授时服务，并且这些网站间应该能够相互比对，提高准确度。
	 * 既可访问NTP服务器, 又可以访问SNTP服务器
	 * @param host String host
	 * @param port int port
	 */
	public static Date ntp(String host, int port){
		long currentTime = 0L;
		NTPUDPClient ntpClient = new NTPUDPClient();
		ntpClient.setDefaultTimeout(10000);
		try {
			ntpClient.open();
			TimeInfo timeInfo = ntpClient.getTime(InetAddress.getByName(host), port);
			timeInfo.computeDetails();
			
			System.out.println("NTP消息来回一个周期的时延 : " + timeInfo.getDelay());
			System.out.println("和时钟源服务器的时间差 : " + timeInfo.getOffset()); //时间差, 单位可能是毫秒
			currentTime = System.currentTimeMillis() + timeInfo.getOffset(); //调整本地时钟
		} catch (Exception e) {
			e.printStackTrace();
		}
		ntpClient.close();
		return new Date(currentTime);
	}
	public static Date ntp(String host){
		return ntp(host, 123);
	}
	
	public static void sntp(String host, int port){
//		SNTP
	}
	
	/**
	 * RFC 867 ：Daytime Protocol 服务器监听端口13，以固定字符串的格式将时间返回给客户端，中国授时中心的服务器貌似使用的这种协议
	 */
	public static void daytime(String host){
		DaytimeTCPClient client = new DaytimeTCPClient();

        try {
			// We want to timeout if a response takes longer than 60 seconds
			client.setDefaultTimeout(60000);
			client.connect(host);
			System.out.println(client.getTime().trim());
			client.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static final void daytimeUDP(String host) throws IOException
    {
        DaytimeUDPClient client = new DaytimeUDPClient();

        // We want to timeout if a response takes longer than 60 seconds
        client.setDefaultTimeout(60000);
        client.open();
        System.out.println(client.getTime(
                                          InetAddress.getByName(host)).trim());
        client.close();
    }
	/**
	 * RFC 868 ：Time Protocol 服务器监听37端口，以一个32bit的整数返回当前距离1900年1月1日0点0分0秒的秒数。
	 */
	public static void time(String host){
		TimeTCPClient client = new TimeTCPClient();
	    try {
          // We want to timeout if a response takes longer than 60 seconds
          client.setDefaultTimeout(60000);
          client.connect(host);
          System.out.println(client.getDate());
	    }catch(Exception e){
	    	e.printStackTrace();	  
	    } finally {
	          try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public static void telnet(String host, int port, String user, String password){
		TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
        SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
        TelnetClient tc = new TelnetClient();
        try {
			tc.addOptionHandler(ttopt);
			tc.addOptionHandler(echoopt);
			tc.addOptionHandler(gaopt);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		try {
			tc.connect(host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStream is = tc.getInputStream();
		PrintStream ps = new PrintStream(tc.getOutputStream());
		ps.println("ls");
		ps.flush();
		
		StringBuffer sb = new StringBuffer();
		final String osTag = "$";// 系统标示符号
		try {
			char ch = (char)is.read();
			while (true) { 
				sb.append(ch);
				if (ch == osTag.charAt(osTag.length() - 1)) { 
					if (sb.toString().endsWith(osTag)){
						System.out.println(sb.toString()); 
					}
				}
				ch = (char) is.read(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
