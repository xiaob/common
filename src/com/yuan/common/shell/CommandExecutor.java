package com.yuan.common.shell;

import java.util.List;

public interface CommandExecutor {

	public void exec(List<String> args)throws Exception;
	
}
