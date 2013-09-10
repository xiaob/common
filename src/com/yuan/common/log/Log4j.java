package com.yuan.common.log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.yuan.common.util.SystemTool;

public class Log4j {
	
	public static void load(Class<?> clazz, String configDir){
		String appRootDir = SystemTool.getAppPath(clazz);
		
		String logFileName = appRootDir + File.separator + "log.properties";
		if(!new File(logFileName).exists()){
			File dir = new  File(appRootDir).getParentFile();
			appRootDir = dir.getAbsolutePath();
			if(new File(appRootDir + File.separator + "log.properties").exists()){
				logFileName = appRootDir + File.separator + "log.properties";
			}else{
				logFileName = appRootDir + File.separator + configDir + "/resources/log.properties";
			}
		}
		System.setProperty("myapp.root", appRootDir);
		File parent = new File(appRootDir).getParentFile();
		if(parent != null){
			System.setProperty("myapp.parent", parent.getAbsolutePath());
		}
		
		System.out.println("logFileName = " + logFileName);
		PropertyConfigurator.configure(logFileName);
	}
	
	public static void main(String args[])throws Exception{
		Log4j.load(Log4j.class, "src");
		final Logger log = Logger.getLogger(Log4j.class);
		
		while(true){
			TimeUnit.SECONDS.sleep(1);
			log.info("11111111111111");
		}
	}
	
}
