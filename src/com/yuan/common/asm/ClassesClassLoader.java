package com.yuan.common.asm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.collection.ListEnumeration;
import com.yuan.common.file.FileUtil;


public class ClassesClassLoader extends AbstractClassLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassesClassLoader.class);
	
	private List<String> classesPathList;
	
	public ClassesClassLoader(String classesPath, ClassLoader parent){
		super(parent);
		classesPathList = new ArrayList<String>();
		classesPathList.add(classesPath);
	}
	public ClassesClassLoader(List<String> classesPathList, ClassLoader parent){
		super(parent);
		this.classesPathList = classesPathList;
	}
	
	protected File searchFile(String child){
		for(String classesPath : classesPathList){
			File f = new File(new File(classesPath), child);
			if(f.exists()){
				return f;
			}
		}
		return null;
	}
	protected byte[] loadClassData(String name) throws ClassNotFoundException{
        String child = name.replaceAll("\\.", "/") + ".class";
        File classFile = searchFile(child);
        if(classFile != null){
        	try {
				return FileUtil.readBinaryFile(classFile);
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
				throw new ClassNotFoundException(e.getMessage(), e);
			}
        }

        throw new ClassNotFoundException(name);
    }
	
	protected URL findResource(String name) {
		File f = searchFile(name);
		if(f != null){
			try {
				return f.toURI().toURL();
			} catch (MalformedURLException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
		return null;
	}
	protected Enumeration<URL> findResources(String name) throws IOException {
		ListEnumeration<URL> list = new ListEnumeration<URL>();
		URL url = findResource(name);
		if(url != null){
			list.add(url);
		}
		return list;
	}

}
