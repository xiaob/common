package com.yuan.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.Socket;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {

	private static final Logger log = LoggerFactory.getLogger(Tools.class);
	
	public static String toString(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}
	
	/**
	 * 格式化浮点数
	 * @param f Float
	 * @param pattern String 模式。例如".00"表示小数点后保留两位
	 * @return
	 */
	public static String format(Float f, String pattern){
		
		return new DecimalFormat(pattern).format(f);
	}
	
	/**
	 * 判断是否为BIG5字符编码
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean isBIG5(InputStream is)throws IOException {
		byte[] array = new byte[2];
		int tmp = 0;
		if((is.read(array))!= -1) {
			// 两个字节转换为整数
			tmp = (short)((array[0] << 8)|(array[1] & 0xff));
			tmp = tmp & 0xFFFF;
			return (tmp >= 0xA440 && tmp < 0xFFFF);
		}
		return false;
	}
	
	public static boolean isBIG5(String fileName)throws IOException{
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			return isBIG5(is);
		}finally{
			if(is != null)
				is.close();
		}
		
	}
	
	public static byte[] readStream(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = is.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		return baos.toByteArray();
	}
	
	public static String readReader(Reader reader) throws IOException{
	 	BufferedReader bufReader = new BufferedReader(reader);
		StringWriter writer = new StringWriter();
		String line = null;
		while((line = bufReader.readLine())!=null){
			writer.append(line);
		}
		
		return writer.toString();
	}
	
	/**
	 * 向主机的端口发送一串文本命令
	 * @param host String 
	 * @param port int
	 * @param command String
	 * @return boolean
	 */
	public static boolean sendCommand(final String host, final int port, final String command){
		boolean isOk = true;
		Socket socket = null;
		BufferedOutputStream bos = null;
		try {
			socket = new Socket(host, port);
			bos = new BufferedOutputStream(socket.getOutputStream());
			
			bos.write(command.getBytes("UTF-8"));
			bos.flush();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			isOk = false;
		}finally{
			isOk = closeOutputStream(bos);
			isOk = closeSocket(socket);
		}//try
		return isOk;
	}
	public static boolean closeInputStream(InputStream is){
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	public static boolean closeOutputStream(OutputStream os){
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	public static boolean closeSocket(Socket socket){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 删除文本中的HTML标记
	 * @param htmlText 
	 * @return
	 */
	public static String removeHtmlTag(String htmlText){
		String regex = "<.+?>";
		return htmlText.replaceAll(regex, "");
	}
	
	/**
	 * 使用简明的方式表示大的数量
	 * @param number long
	 * @return String
	 */
	public static String parseMetricUnits(long number){
		
		long ahundredmillion = 0L;
		long tenthousand = 0L;
		long entries = 0L; 
		long tmp = 0L;
		
		if(number < 10){
			return number + "条";
		}
		
		tmp = number;
		entries = tmp % 10000L;
		tenthousand = tmp / 10000L;
		if(tenthousand < 10000){
			return tenthousand + "万" + entries + "条";
		}
		
		tmp = tenthousand;
		tenthousand = tmp % 10000L;
		ahundredmillion = tmp / 10000L;
		
		return ahundredmillion + "亿" +  tenthousand + "万" + entries + "条";
	}
	
}
