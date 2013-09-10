package com.yuan.common.swing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.yuan.common.util.SystemTool;


public class ResourceManager {
	
	private Map<String, String> resourceMap = new HashMap<String, String>();
	private String path;

	public ResourceManager(Class<?> clazz){
		path = SystemTool.getAppPath(clazz);
	}
	
	public void put(String key, String value){
		resourceMap.put(key, path + File.separator + value);
	}
	
	public String get(String key){
		return resourceMap.get(key);
	}
}
