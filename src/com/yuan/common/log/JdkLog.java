package com.yuan.common.log;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import com.yuan.common.util.SystemTool;

public class JdkLog {
	
	public static void load(Class<?> clazz){
		String appRootDir = SystemTool.getAppPath(clazz);
		String logFileName = appRootDir + File.separator + "logging.properties";
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(logFileName));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
