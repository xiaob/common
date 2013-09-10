package com.yuan.common.brpc.tool;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.asm.ClassUtil;
import com.yuan.common.util.StringUtil;

public class MethodGenerator {

	private static final Logger log = LoggerFactory.getLogger(MethodGenerator.class);
	
	private Class<?> clazz;
	private List<MethodView> list = new ArrayList<MethodView>();
	private List<String> importClassList = new ArrayList<String>();

	public MethodGenerator(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}
	
	public void generate(){
		Method[] ms = clazz.getDeclaredMethods();
		for(Method method : ms){
			if(Modifier.isPublic(method.getModifiers()) && (!method.getName().equals("main"))){
				log.info("method.getName() = " + method.getName());
				MethodView methodView = new MethodView();
				methodView.setReturnType(getReturnType(method));
				methodView.setMethodName(method.getName());
				methodView.setParameters(getParameters(method));
				list.add(methodView);
			}
		}
	}
	private String getReturnType(Method method){
		Class<?> clazz = method.getReturnType();
		if(clazz.isPrimitive()){//基本类型
			return clazz.getName();
		}else if(clazz.isArray()){
			if(clazz.getComponentType().isPrimitive()){
				return clazz.getComponentType().getName() + "[]";
			}else{
				importClass(clazz.getComponentType());
				return ClassUtil.getShortName(clazz.getComponentType()) + "[]";
			}
		}
		importClass(clazz);
		
		return ClassUtil.getShortName(clazz);
	}
	private String getParameters(Method method){
		Class<?>[] parameterTypes = method.getParameterTypes();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<parameterTypes.length; i++){
			Class<?> parameterType = parameterTypes[i];
			if(parameterType.isPrimitive()){//基本类型
				sb.append(parameterType.getName()).append(" arg").append(i).append(",");
			}else if(parameterType.isArray()){
				if(parameterType.getComponentType().isPrimitive()){
					String shortClassName = parameterType.getComponentType().getName() + "[]";
					sb.append(shortClassName).append(" arg").append(i).append(",");
				}else{
					importClass(parameterType.getComponentType());
					String shortClassName = ClassUtil.getShortName(parameterType.getComponentType()) + "[]";
					sb.append(shortClassName).append(" arg").append(i).append(",");
				}
			}else{
				importClass(parameterType);
				sb.append(ClassUtil.getShortName(parameterType)).append(" arg").append(i).append(",");
			}
		}
		sb = StringUtil.compareAndDeleteLastChar(sb, ',');
		
		return sb.toString();
	}
	private void importClass(Class<?> clazz){
		if((!clazz.getPackage().getName().equals("java.lang")) && (!importClassList.contains(clazz.getName()))){
			importClassList.add(clazz.getName());
		}
	}

	public List<MethodView> getList() {
		return list;
	}

	public List<String> getImportClassList() {
		return importClassList;
	}
	
}
