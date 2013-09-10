package com.yuan.common.sigar;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.SystemTool;

public class LocalSystem {
	
	private static final Logger log = LoggerFactory.getLogger(LocalSystem.class);
	
	private static Sigar sigar = null;
	private static OperatingSystem os = null;
	
	static{
		SystemTool.appendBinPath(LocalSystem.class);
		
		sigar = new Sigar();
		os = OperatingSystem.getInstance();
		
	}
	
	/**
	 * 获得CPU利用率
	 * @return double
	 */
	public static double getCpuUsageRate(){
		try{
			return sigar.getCpuPerc().getCombined()*100;
		}
		catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
	}
	
	/**
	 * 获得CPU数目
	 * @return int
	 */
	public static int getCpuNum(){
		try{
			return sigar.getCpuInfoList().length;
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
	}
	
	/**
	 * 获得CPU的速度
	 * @param index
	 * @return long
	 */
	public static long getCpuSpeed(int index) {
		try{
			return sigar.getCpuInfoList()[index].getMhz();
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
	}
	
	/**
	 * 获得物理内存使用率
	 * @return double
	 */
	public static double getMemUsageRate(){
		double persent = 0;
		try{
			Mem mem = sigar.getMem();
			long total = mem.getTotal();
			long used = mem.getUsed();		
			
			if (total>0)			
				persent = (double)used/(double)total;
		}
		catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
		return  persent*100;
	}
	
	/**
	 * 获得内存大小, 单位MB
	 * @return long
	 */
	public static long getMemSize(){
		try{
			return sigar.getMem().getRam();
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
	}
	
	/**
	 * 获得硬盘使用率
	 * @return double
	 */
	public static double getDiskUsageRate() {
		double persent = 0;
		long total = 0;
		long used = 0;
		try{
			FileSystem fss[] = sigar.getFileSystemList();			
			for (FileSystem fs: fss){			
				if (fs.getType()==FileSystem.TYPE_LOCAL_DISK){
					FileSystemUsage fsu = sigar.getFileSystemUsage(fs.getDirName());
					total += fsu.getTotal();
					used += fsu.getUsed();
				}
			}//for		
		}
		catch (Exception e){
			log.error(e.getMessage(), e);
			return 0;
		}
		if (total>0)			
			persent = (double)used/(double)total;
		return  persent*100;
	}
	
	/**
	 * 获得硬盘大小, 单位MB
	 * @return long
	 */
	public static long getDiskSize(){		
		long total = 0;	
		try{
			FileSystem fss[] = sigar.getFileSystemList();
			for (FileSystem fs: fss){			
				if (fs.getType()==FileSystem.TYPE_LOCAL_DISK){
					FileSystemUsage fsu = sigar.getFileSystemUsage(fs.getDirName());
					total += fsu.getTotal(); //KB				
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return total/1000;
	}
	
	public static void printAdapterInfoes() {
		try{

			String[] netFaces = sigar.getNetInterfaceList();
			
			for(String name : netFaces){
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(name);
				log.info("网络名 : " + cfg.getName());
				log.info("网络类型 : " + cfg.getType());
				String desc = cfg.getDescription();
				
				log.info("描述 : " + new String(desc.getBytes("iso-8859-1"), "UTF-16"));
				
				log.info("IP地址 : " + cfg.getAddress());
				log.info("子网掩码 : " + cfg.getNetmask());
				log.info("网卡物理地址 : " + cfg.getHwaddr());
				if ((cfg.getFlags() & NetFlags.IFF_UP) > 0)
					log.info("激活");
				else
					log.info("未激活");
				log.info("==============================");
			}
			
			
			//统计网卡流量
			long start = System.currentTimeMillis();   
            NetInterfaceStat statStart = sigar.getNetInterfaceStat("eth0");   
            long rxBytesStart = statStart.getRxBytes(); //接收数据包
            long txBytesStart = statStart.getTxBytes(); //发送数据包
            Thread.sleep(1000);   
            long end = System.currentTimeMillis();   
            NetInterfaceStat statEnd = sigar.getNetInterfaceStat("eth0");   
            long rxBytesEnd = statEnd.getRxBytes();   
            long txBytesEnd = statEnd.getTxBytes();   
               
            long rxbps = (rxBytesEnd - rxBytesStart)*8/(end-start)*1000;   
            long txbps = (txBytesEnd - txBytesStart)*8/(end-start)*1000;   
            log.info("rxbps : " + rxbps + ", txbps : " + txbps);
		}catch (Exception e){
			log.error(e.getMessage(), e);	
		}
	}
	
	/**
	 * 系统启动时间
	 * @return double
	 */
	public static double getUpTime(){
		try{
			return sigar.getUptime().getUptime();
		}
		catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String getOsVersion(){
		return os.getVersion();
	}
	
	public static String getOsVendor(){
		return os.getVendor();
	}
	
	public static String getOsName(){
		return os.getDescription();
	}
	
	public static long getCurrentPid(){
		return sigar.getPid();
	}
	
	public static String getCurrentPname(){
		try {
			return sigar.getProcExe(getCurrentPid()).getName();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static double getCurrentPcpu(){
		try {
			return sigar.getProcCpu(getCurrentPid()).getPercent();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	
	public static void main(String args[])throws Exception{
		LocalSystem.printAdapterInfoes();
	}

}
