package com.yuan.common.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.yuan.common.util.AssertUtil;

public class DefaultFilenameFilter implements FilenameFilter {
	
	private boolean ignoreDir = false; //是否忽略文件夹,默认不忽略
	private List<String> extList = new ArrayList<String>();
	
	public DefaultFilenameFilter(final String ext){
		this.extList.add("."+ext);
	}
	public DefaultFilenameFilter(final String... exts){
		if((exts != null) && (exts.length > 0)){
			for(String ext : exts){
				this.extList.add("."+ext);
			}
		}
		
	}
	
	public DefaultFilenameFilter(final String ext, boolean ignoreDir){
		this.ignoreDir = ignoreDir;
		this.extList.add("."+ext);
	}
	
	public void addExt(final String ext){
		this.extList.add("."+ext);
	}
	
	public boolean accept(File dir, String name) {
		if(ignoreDir){
			if(new File(dir+File.separator+name).isDirectory()){
				return false;
			}else{
				return endsWithExt(name);
			}
		}else{
			return endsWithExt(name)||new File(dir+File.separator+name).isDirectory();
		}//if
	}//accept
	
	protected boolean endsWithExt(String name){
		if(!AssertUtil.notEmpty(extList)){
			return true;
		}
		for(String ext : extList){
			if(name.endsWith(ext)){
				return true;
			}
		}
		
		return false;
	}

}
