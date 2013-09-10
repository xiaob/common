package com.yuan.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class PropertyFile {
	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public PropertyFile(String file) throws IOException{
		this(new File(file));
		
	}
	
	public PropertyFile(File file) throws IOException{
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(file);
			load(fis);
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		
	}
	
	public PropertyFile(Class<?> clazz, String name) throws URISyntaxException, IOException{
		this(FileUtil.getFileFromClassPath(clazz, name));
		
	}
	
	public PropertyFile(InputStream is) throws IOException{
		load(is);
		
	}
	
	private void load(InputStream is)throws IOException{
		Properties properties = new Properties();
		properties.load(is);
		Enumeration<?> pName = properties.propertyNames();
		while(pName.hasMoreElements()){
			String key = (String)pName.nextElement();
			String value = properties.getProperty(key);
			map.put(key, value);
		}
	}
	
	public String getValue(String key){
		return map.get(key);
	}
	
	public Integer getInt(String key){
		if(map.containsKey(key)){
			return Integer.parseInt(map.get(key));
		}
		
		return null;
	}
	
	public Long getLong(String key){
		if(map.containsKey(key)){
			return Long.parseLong(map.get(key));
		}
		
		return null;
	}
	
}
