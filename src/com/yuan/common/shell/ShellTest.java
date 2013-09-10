package com.yuan.common.shell;

import java.util.List;

public class ShellTest {

	public static void main(String[] args) {
		Shell shell = new Shell("yuan");
		shell.addCommand(new ShellCommand() {
			public String getHelp() {
				return null;
			}
			public String getCommandText() {
				return "sum";
			}
			public void exec(List<String> args) throws Exception {
				Integer sum = 0;
				for(String arg : args){
					sum += Integer.parseInt(arg);
				}
				System.out.println("sum = " + sum);
			}
		});
		shell.start();
	}

}
