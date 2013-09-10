package com.yuan.common.shell;


public interface ShellCommand extends CommandExecutor{

	public String getCommandText();
	public String getHelp();
	
}
