package com.yuan.common.brpc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuan.common.brpc.ServiceProxyFactory;
import com.yuan.common.shell.CommandExecutor;
import com.yuan.common.shell.Shell;

public class Client {

	public static void main(String[] args) {
		final String host = "127.0.0.1";
		final int port = 1000;
		
		Shell shell = new Shell("远程方法调用");
		shell.addCommand("hello", "hello", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				Hello proxy = ServiceProxyFactory.createProxy(Hello.class, host, port);
				long totalTime = 0L;
				int count = 200;
				for(int i=0; i<count; i++){
					long start = System.currentTimeMillis();
					proxy.hello("hello message");
					long end = System.currentTimeMillis();
					show("耗时：" + (end - start) + "毫秒");
					totalTime += (end - start);
				}
				show("平均耗时: " + totalTime/count + "毫秒");
				show(proxy.hello("hello message"));
			}
		});
		shell.addCommand("isChinese", "isChinese char", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				IStringUtil proxy = ServiceProxyFactory.createProxy(IStringUtil.class, host, port);
				show("" + proxy.isChinese(args.get(0).charAt(0)));
			}
		});
		shell.addCommand("demo1", "demo1", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				IDemoTest proxy = ServiceProxyFactory.createProxy(IDemoTest.class, host, port);
				show(proxy.demo1().toString());
			}
		});
		shell.addCommand("demo3", "demo3", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				IDemoTest proxy = ServiceProxyFactory.createProxy(IDemoTest.class, host, port);
				show(proxy.demo3().toString());
			}
		});
		shell.addCommand("demo4", "demo4", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				IDemoTest proxy = ServiceProxyFactory.createProxy(IDemoTest.class, host, port);
				show(proxy.demo4().toString());
			}
		});
		shell.addCommand("demo8", "demo8", new CommandExecutor() {
			public void exec(List<String> args) throws Exception {
				IDemoTest proxy = ServiceProxyFactory.createProxy(IDemoTest.class, host, port);
				PojoModel model1 = new PojoModel(1, "wang", 12.34d, new Date());
				PojoModel model2 = new PojoModel(2, "zhang", 56.78d, new Date());
				
				List<PojoModel> list = new ArrayList<PojoModel>();
				list.add(model1);
				list.add(model2);
				proxy.demo8(1L, new Date(), model1, list);
			}
		});
		shell.start();
	}
	
	private static void show(String message){
		System.out.println(message);
	}

}
