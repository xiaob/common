package com.yuan.common.brpc.tool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.asm.ClassUtil;
import com.yuan.common.compress.JarTool;
import com.yuan.common.file.FileUtil;
import com.yuan.common.file.TemplateCache;
import com.yuan.common.util.CompileUtil;
import com.yuan.common.util.StringUtil;
import com.yuan.common.util.SystemTool;
import com.yuan.common.xml.Dom;


public class ProxyInterfaceGenerator {

	private static final Logger log = LoggerFactory.getLogger(ProxyInterfaceGenerator.class);
	
	public static void main(String[] args) {
		String proxyTemplateDir = SystemTool.searchResource(ProxyInterfaceGenerator.class, "proxyTemplate", "proxyTemplate");
		File appHome = new File(proxyTemplateDir).getParentFile();
		File destDir = new File(appHome, "dest");
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		File srcDir = new File(appHome, "src");
		if(!srcDir.exists()){
			srcDir.mkdirs();
		}
		File binDir = new File(appHome, "bin");
		if(!binDir.exists()){
			binDir.mkdirs();
		}
		TemplateCache templateCache = new TemplateCache(proxyTemplateDir);
		try {
			templateCache.loadTemplate();
		} catch (IOException e1) {
			log.warn(e1.getMessage(), e1);
		}
		
		String xml = SystemTool.searchResource(ProxyInterfaceGenerator.class, "proxy-gen.xml", "conf");
		Dom root = Dom.getRoot(xml);
		
		List<Dom> serviceElementList = root.element("services").elements("service");
		for(Dom serviceElement : serviceElementList){
			generateCode(srcDir.getAbsolutePath(), templateCache, serviceElement.getElementText());
		}
		if(root.elementText("autoCompileAndPackage").equals("true")){
			try {
				CompileUtil.defaultCompile(appHome.getAbsolutePath(), null);
				JarTool.compressFolder(binDir.getAbsolutePath(), new File(destDir, "hxapi-proxy.jar").getAbsolutePath());
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
		}

	}
	
	public static void generateCode(String srcDir, TemplateCache proxyTemplate, String serviceClassName){
		try{
			Class<?> clazz = Class.forName(serviceClassName);
			String packageName = clazz.getPackage().getName();
			String longClassName = clazz.getName();
			String shortClassName = ClassUtil.getShortName(clazz);
			String proxyInterfaceName = "I" + shortClassName + ".java";
			MethodGenerator methodGenerator = new MethodGenerator(clazz);
			methodGenerator.generate();
			
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("packageName", packageName);
			rootMap.put("longClassName", longClassName);
			rootMap.put("shortClassName", shortClassName);
			rootMap.put("importClassList", methodGenerator.getImportClassList());
			rootMap.put("methodList", methodGenerator.getList());
			generateCode(srcDir, packageName,proxyTemplate, proxyInterfaceName, "Proxy", rootMap);
		}catch(Exception e){
			log.warn(e.getMessage(), e);
		}
	}
	
	public static void writeFile(String srcDir, String packageName, String code, String fileName, String subPackage){
		try {
			String[] pArr = packageName.split("\\.");
			String packagePath = "";
			for(String p : pArr){
				packagePath += File.separator + p;
			}
			String path = srcDir + packagePath;
			if(StringUtil.hasText(subPackage)){
				path += File.separator + subPackage;
			}
			if(!new File(path).exists()){
				new File(path).mkdirs();
			}
			String file = path + File.separator + fileName;
			FileUtil.writeToFile(code, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void generateCode(String srcDir, String packageName, TemplateCache templateCache, String fileName, String templateName, Map<String, Object> rootMap){
		try {
			String code = templateCache.fillTemplate(templateName, rootMap);
			writeFile(srcDir, packageName, code, fileName, "proxy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
