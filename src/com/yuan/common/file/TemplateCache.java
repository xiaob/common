package com.yuan.common.file;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;

public class TemplateCache {
	
	protected final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	protected final Lock readLock = readWriteLock.readLock();
	protected final Lock writeLock = readWriteLock.writeLock();
	
	private Map<String, Template> templateMap = new HashMap<String, Template>();
	
	private String templateDir;
	
	public TemplateCache(){
		super();
	}
	
	public TemplateCache(String templateDir){
		this.templateDir = templateDir;
	}
	
	public boolean hasTemplate(){
		return templateMap.size() > 0;
	}
	
	public Template getTemplate(String templateName){
		readLock.lock();
		try{
			return templateMap.get(templateName);
		}finally{
			readLock.unlock();
		}
	}
	
	public Map<String, Template> getTemplateMap() {
		return templateMap;
	}

	public void setTemplateMap(Map<String, Template> templateMap) {
		this.templateMap = templateMap;
	}
	
	public int getTemplateCount(){
		readLock.lock();
		try{
			if(this.templateMap == null){
				return 0;
			}
			return this.templateMap.size();
		}finally{
			readLock.unlock();
		}
	}
	
	public String toString(){
		readLock.lock();
		try{
			if(this.templateMap == null){
				return "[]";
			}
			return this.templateMap.keySet().toString();
		}finally{
			readLock.unlock();
		}
	}
	
	public List<String> getTemplateNameList(){
		List<String> templateNameList = new ArrayList<String>();
		if(this.hasTemplate()){
			Set<String> keys = null;
			readLock.lock();
			try{
				keys = this.templateMap.keySet();
			}finally{
				readLock.unlock();
			}
			templateNameList.addAll(keys);
		}
		return templateNameList;
	}
	
	public void addTemplate(String templateName, Template template){
		writeLock.lock();
		try{
			this.templateMap.put(templateName, template);
		}finally{
			writeLock.unlock();
		}
	}
	
	public void loadTemplate()throws IOException{
		Configuration cfg = new Configuration();
        cfg.setEncoding(Locale.CHINA, "UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(templateDir));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        List<String > templateFileList = getTemplateFileList(templateDir);
        for(String templateFile : templateFileList){
        	String templateName = "";
        	if(templateFile.lastIndexOf(".") == -1){
        		templateName = templateFile;
        	}else{
        		templateName = templateFile.substring(0, templateFile.lastIndexOf("."));
        	}
			Template template = cfg.getTemplate(templateFile);
			this.addTemplate(templateName, template);
        }
	}
	
	//从模板目录中获取所有的模板文件名
	private List<String> getTemplateFileList(String templateDir){
		List<String> templateFileList = new ArrayList<String>();
		File dir = new File(templateDir);
		if(!dir.exists()){
			return templateFileList;
		}
		templateFileList = Arrays.asList(dir.list(new DefaultFilenameFilter("ftl", true)));
		return templateFileList;
	}
	
	public String fillTemplate(final String templateName, Map<String, Object> rootMap)throws TemplateException, IOException{
		Template template = this.getTemplate(templateName);
		if (template == null){
			throw new IOException(templateName + "模板不存在. ");
		}
//		logger.info("template.getEncoding() = " + template.getEncoding());
		StringWriter sw = new StringWriter();
		template.process(rootMap, sw);
		sw.flush();
		return sw.toString();
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}
	
	public static void main(String args[])throws Exception{
		final String templateDir = "D:\\workspace2\\tae\\source\\trunk\\WebRoot\\WEB-INF\\templates";
		TemplateCache templateCache = new TemplateCache(templateDir);
		templateCache.loadTemplate();
		Template template = templateCache.getTemplate("index");
		System.out.println("========================");
		TemplateSequenceModel tsm = template.getRootTreeNode().getChildNodes();
		int d = 0;
		try {
			d= tsm.size();
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		for(int i=0;i<d;i++){
			try {
				System.out.println(tsm.get(i));
			} catch (TemplateModelException e) {
				e.printStackTrace();
			}
		}
	}
	
}
