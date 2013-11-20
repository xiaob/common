package com.yuan.common.mysql;

public interface StatusListener {
	
	public void started(String fileName);
	public void status(long size);
	public void finished(long size);
	
}
