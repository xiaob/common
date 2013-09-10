package com.yuan.common.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源访问工厂
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class ResourceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceFactory.class);
	
	private File appRoot;
	
	public ResourceFactory(String systemProperty){
		if((systemProperty != null) && (System.getProperty(systemProperty) != null)){
			appRoot = new File(System.getProperty(systemProperty));
		}else{
			try {
				appRoot = new File("").getCanonicalFile();
			} catch (IOException e) {
				LOG.warn(e.getMessage(), e);
			}
		}
	}
	
	public File getResource(String name){
		if(appRoot == null){
			throw new IllegalStateException("ResourceFactory init failed! appRoot is null");
		}
		
		return new File(appRoot, name);
	}

}
