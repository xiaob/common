package com.yuan.common.swing;

public interface LoginListener {

	public void login(LoginDialog owner, String username, String password);
	public void giveup(LoginDialog owner);	
	
}
