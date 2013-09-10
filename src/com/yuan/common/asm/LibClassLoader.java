package com.yuan.common.asm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.collection.ListEnumeration;
import com.yuan.common.compress.JarTool;
import com.yuan.common.file.DefaultFilenameFilter;

public class LibClassLoader extends AbstractClassLoader {

	private static final Logger logger = LoggerFactory.getLogger(LibClassLoader.class);
	
	protected List<File> jarList = new ArrayList<File>();
	
	public LibClassLoader(String libPath, ClassLoader parent){
		super(parent);
		File[] jars = new File(libPath).listFiles(new DefaultFilenameFilter("jar", true));
		jarList = Arrays.asList(jars);
	}
	public LibClassLoader(List<String> libPathList, ClassLoader parent){
		super(parent);
		for(String libPath : libPathList){
			File[] jars = new File(libPath).listFiles(new DefaultFilenameFilter("jar", true));
			jarList.addAll(Arrays.asList(jars));
		}
	}
	
	protected byte[] loadClassData(String name)throws ClassNotFoundException {
        String classEntryName = name.replaceAll("\\.", "/") + ".class";
        byte[] classData = null;
        for(File jarFile : jarList) {
        	try {
				classData = JarTool.readEntry(jarFile.getAbsolutePath(), classEntryName);
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
				throw new ClassNotFoundException(e.getMessage(), e);
			}
        	if(classData != null ){
        		break;
        	}
        }
        if(classData == null){
        	throw new ClassNotFoundException(name);
        }
		return classData;
    }
	
	protected URL findResource(String name) {
		for (File jarFile : jarList) {
			try {
				if (JarTool.existsEntry(jarFile.getAbsolutePath(), name)) {
					return getJarUrl(jarFile.getAbsolutePath(), name);
				}
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		return null;
	}

	protected Enumeration<URL> findResources(String name) throws IOException {
		ListEnumeration<URL> list = new ListEnumeration<URL>();
		for (File jarFile : jarList) {
			try {
				if (JarTool.existsEntry(jarFile.getAbsolutePath(), name)) {
					list.add(getJarUrl(jarFile.getAbsolutePath(), name));
				}
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		return list;
	}
	
	protected URL getJarUrl(String jarFile, String entryName){
		try {
			return new URL("jar:file:/"+jarFile+"!/"+entryName);
		} catch (MalformedURLException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

}
