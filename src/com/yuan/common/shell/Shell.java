package com.yuan.common.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.AssertUtil;
import com.yuan.common.util.StringUtil;


/**
 * 命令格式：命令 参数1,参数2,参数3
 * @author yuan
 *
 */
public class Shell {

	private static final Logger log = LoggerFactory.getLogger(Shell.class);
	
	public static String COMMAND_SEPARATOR = " ";
	public static String ARGS_SEPARATOR = ",";
	
	private Map<String, ShellCommand> commandMap = new HashMap<String, ShellCommand>();
	private AtomicBoolean running = new AtomicBoolean(true);
	private String shellName = "shell";
	private List<CommandExecutor> shutdownHookList = new ArrayList<CommandExecutor>();
	
	public Shell(){
		addCommand(new ExitCommand());
		addCommand(new HelpCommand());
	}
	
	public Shell(String shellName){
		this();
		this.shellName = shellName;
	}
	
	public void addCommand(ShellCommand command){
		commandMap.put(command.getCommandText(), command);
	}
	public void addCommand(final String commandText, final String help, final CommandExecutor executor){
		ShellCommand command = new ShellCommand(){
			public void exec(List<String> args) throws Exception {
				executor.exec(args);
			}
			public String getCommandText() {
				return commandText;
			}
			public String getHelp() {
				return help;
			}
			
		};
		
		commandMap.put(command.getCommandText(), command);
	}
	
	public void start(){
		
		cmdLoop();
	}
	
	public void shutdown() throws Exception{
		running.set(false);
		
		for(CommandExecutor executor : shutdownHookList){
			executor.exec(null);
		}
	}
	
	public void addShutdownHook(CommandExecutor executor){
		shutdownHookList.add(executor);
	}
	
	/**
	 * 命令循环，等待用户输入命令
	 */
	protected void cmdLoop(){
		while(running.get()){
			System.out.print(shellName + ">");
			String line = readLine();
			if(StringUtil.hasText(line)){
				String[] commandArr = line.split(COMMAND_SEPARATOR);
				if(commandMap.containsKey(commandArr[0])){
					String args = null;
					if(commandArr.length > 1){
						args = commandArr[1];
					}
					try {
						commandMap.get(commandArr[0]).exec(processArgs(args));
					} catch (Exception e) {
						show(e.getMessage());
						e.printStackTrace();
					}
				}else{
					show("命令" + commandArr[0] + "不存在！");
				}
			}else{
				help();
			}
		}
	}
	
	protected List<String> processArgs(String args){
		if(!StringUtil.hasText(args)){
			return null;
		}
		String[] argArr = args.split(ARGS_SEPARATOR);
		
		return Arrays.asList(argArr);
	}
	
	/**
	 * TAB命令智能补全
	 */
	protected void tabBash(){
		
	}
	
	/**
	 * 读取控制台输入，阻塞方法
	 * @return String
	 */
	protected String readLine(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			return br.readLine();
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 向控制台打印消息
	 * @param message String 
	 */
	protected void show(String message){
		
		System.out.println(message);
	}
	
	public void help(){
		for(String command : commandMap.keySet()){
			show(command);
		}
	}
	
	private class ExitCommand implements ShellCommand{

		@Override
		public void exec(List<String> args) throws Exception {
			shutdown();
			
		}

		@Override
		public String getCommandText() {
			
			return "exit";
		}

		@Override
		public String getHelp() {
			
			return "";
		}
		
	}
	private class HelpCommand implements ShellCommand{

		@Override
		public void exec(List<String> args) throws Exception {
			if(AssertUtil.notEmpty(args)){
				String command = args.get(0);
				if(commandMap.containsKey(command)){
					show(commandMap.get(command).getHelp());
				}else{
					show("命令" + command + "不存在！");
				}
			}else{
				help();
			}
			
		}

		@Override
		public String getCommandText() {
			
			return "help";
		}

		@Override
		public String getHelp() {
			
			return "";
		}
		
	}
}
