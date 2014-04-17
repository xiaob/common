package com.yuan.common.util;

import java.io.File;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.yuan.common.file.FileUtil;

public class Script {
	
	private ScriptEngine scriptEngine;
	private SimpleBindings bindings = new SimpleBindings();
	
	public Script(){
		ScriptEngineManager sem = new ScriptEngineManager();
		scriptEngine = sem.getEngineByName("js");
	}
	
	public void bindObject(String var, Object obj){
		bindings.put(var, obj);
	}
	
	public Object exec(String script)throws ScriptException{
		if(bindings.isEmpty()){
			return scriptEngine.eval(script);
		}
		return scriptEngine.eval(script, bindings);
	}
	
	/**
	 * 调用脚本中的函数, 和bindObject互斥
	 * @param functionName String
	 * @param args Object[]
	 * @return Object
	 */
	public Object invokeFunction(String functionName, Object... args)throws ScriptException, NoSuchMethodException{
		if(!bindings.isEmpty()){
			return null;
		}
		Invocable invocable = (Invocable)scriptEngine; 
		return invocable.invokeFunction(functionName, args);
	}
	
	public Object get(String key){
		return scriptEngine.get(key);
	}
	public void put(String key, Object value){
		scriptEngine.put(key, value);
	}
	
	public static void main(String args[])throws Exception{
		File f = new File(new File("").getCanonicalFile(), "resource/my.js");
		String js = FileUtil.readText(f.toPath());
		
		Script script = new Script();
		script.exec(js);
	}
	protected static void test1()throws Exception{
		// Packages是脚本语言里的一个全局变量,专用于访问JDK的package
		String js = "importPackage(javax.swing);importPackage(java.lang);function doSwing(t){var f=new Packages.javax.swing.JFrame(t);f.setSize(400,300);f.setVisible(true);} ";
//				String js = "function doSwing(t){return Packages.java.lang.Math.abs(-2323872);}";
		Script script = new Script();
//				script.bindObject("a", new Student());
	    js = "function test1(){var 变量1 = 1; var 变量2 = 2; return 变量1 + 变量2}";
		script.exec(js);
//				Object o = script.invokeFunction("doSwing", "测试");
		Object o = script.invokeFunction("test1");
		System.out.println(o);
	}

}
