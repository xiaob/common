package com.yuan.common.log;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import com.yuan.common.util.SystemTool;

public class Slf4j {
	
	public static void load(Class<?> clazz) {
		String logFileName = SystemTool.searchResource(clazz, "logback.xml", "conf");
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(loggerContext);
		loggerContext.reset();
		try {
			configurator.doConfigure(logFileName);
		} catch (JoranException e) {
			e.printStackTrace();
		}
	}

}
