package com.yuan.common.asm;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MemberNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.AssertUtil;

public class DynamicClass {

	private Logger logger = LoggerFactory.getLogger(DynamicClass.class);
	
	private ClassNode classNode = new ClassNode();
	private Map<String, Type> fieldMap = new LinkedHashMap<String, Type>();
	private Map<String, String> signatureMap = new LinkedHashMap<String, String>();
	
	public DynamicClass(String classInternalName){
		this(classInternalName, Object.class);
	}
	
	public DynamicClass(String classInternalName, Class<?> superClass){
		classNode.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER;
		classNode.name = classInternalName;
		classNode.superName = Type.getInternalName(superClass);
		classNode.version = Opcodes.V1_6;
	}
	
	public void addClassAnnotation(AnnotationInfo... annotationInfos){
		if(classNode.visibleAnnotations == null){
			classNode.visibleAnnotations = new ArrayList<AnnotationNode>();
		}
		if(AssertUtil.notEmpty(annotationInfos)){
			for(AnnotationInfo annotationInfo : annotationInfos){
				classNode.visibleAnnotations.add(annotationInfo.toAnnotationNode());
			}
		}
	}
	
	public void addField(String typeClassName, String parameterTypeClassName, String name, AnnotationInfo... annotationInfos){
		Type type = Type.getObjectType(ClassUtil.getInternalClassName(typeClassName));
		Type parameterType = null;
		if(parameterTypeClassName != null){
			parameterType = Type.getObjectType(ClassUtil.getInternalClassName(parameterTypeClassName));
		}
		addField(type, parameterType, name, annotationInfos);
	}
	public void addField(Class<?> typeClass, Class<?> parameterTypeClass, String name, AnnotationInfo... annotationInfos){
		Type type = Type.getType(typeClass);
		Type parameterType = null;
		if(parameterTypeClass != null){
			parameterType = Type.getType(parameterTypeClass);
		}
		addField(type, parameterType, name, annotationInfos);
	}
	
	public void addField(Type type, Type parameterType, String name, AnnotationInfo... annotationInfos){
		String desc = type.getDescriptor();
		String signature = null;
		if(parameterType != null){
			signature = desc.substring(0, desc.length() - 1) + "<" + parameterType.getDescriptor() + ">;";
			signatureMap.put(name, signature);
		}
		FieldNode fieldNode = new FieldNode(Opcodes.ACC_PUBLIC, name, desc, signature, null);
		if(fieldNode.visibleAnnotations == null){
			fieldNode.visibleAnnotations = new ArrayList<AnnotationNode>();
		}
		if(AssertUtil.notEmpty(annotationInfos)){
			for(AnnotationInfo annotationInfo : annotationInfos){
				fieldNode.visibleAnnotations.add(annotationInfo.toAnnotationNode());
			}
		}
		classNode.fields.add(fieldNode);
		fieldMap.put(name, type);
	}
	
	public Object create(){
		try {
			return createClass().newInstance();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return null;
	}
	
	public Class<?> createClass(){
		byte[] data = getClassData();
		
		ByteClassLoader classLoader = new ByteClassLoader(this.getClass().getClassLoader());
		classLoader.putClassData(getClassName(), data);
		try {
			return classLoader.loadClass(Type.getObjectType(classNode.name).getClassName());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return null;
	}
	
	public byte[] getClassData(){
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(new AddBeanMethodClassAdapter(cw, classNode.name, classNode.superName, fieldMap, signatureMap));
		byte[] data = cw.toByteArray();
		return data;
	}
	
	public String getClassName(){
		return Type.getObjectType(classNode.name).getClassName();
	}
	
	public InputStream getClassInputStream(){
		return new ByteArrayInputStream(getClassData());
	}
	
	public void writeToFile(String classFile){
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(classFile));
			bos.write(getClassData());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
		
	}
	
}

//添加一个空构造方法, 给每一个字段添加GET/SET方法
class AddBeanMethodClassAdapter extends ClassAdapter implements Opcodes{
	
	private String superClassInternalName;
	private String classInternalName;
	private Map<String, Type> fieldMap;
	private Map<String, String> signatureMap; 
	
	public AddBeanMethodClassAdapter(ClassVisitor cv, String classInternalName, String superClassInternalName, Map<String, Type> fieldMap, Map<String, String> signatureMap){
		super(cv);
		this.classInternalName = classInternalName;
		this.superClassInternalName = superClassInternalName;
		this.fieldMap = fieldMap;
		this.signatureMap = signatureMap;
	}
	
	public void visitEnd(){
		addEmptyConstructor();
		
		if(!AssertUtil.notEmpty(fieldMap)){
			return ;
		}
		Set<String> nameSet = fieldMap.keySet();
		for(String name : nameSet){
			addGetMethod(name, fieldMap.get(name));
			addSetMethod(name, fieldMap.get(name));
		}
	}
	
	protected void addEmptyConstructor(){
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, superClassInternalName, "<init>", "()V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}
	protected void addGetMethod(String name, Type type){
		String desc = type.getDescriptor();
		String methodDesc = "()" + desc;
		String signature = null;
		if(AssertUtil.notEmpty(signatureMap) && signatureMap.containsKey(name)){
			signature = "()" + signatureMap.get(name);
		}
		
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, getGetMethodName(name), methodDesc, signature, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		Op.pushInputParam(mv, methodDesc);
		mv.visitFieldInsn(GETFIELD, classInternalName, name, desc);
		Op.returns(mv, methodDesc);
	    // max stack and max locals automatically computed
	    mv.visitMaxs(0, 0);
		mv.visitEnd();
	}
	protected void addSetMethod(String name, Type type){
		String desc = type.getDescriptor();
		String methodDesc = "("+desc+")V";
		String signature = null;
		if(AssertUtil.notEmpty(signatureMap) && signatureMap.containsKey(name)){
			signature = "("+signatureMap.get(name)+")V";
		}
		
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, getSetMethodName(name), methodDesc, signature, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		Op.pushInputParam(mv, methodDesc);
		mv.visitFieldInsn(PUTFIELD, classInternalName, name, desc);
		Op.returns(mv, methodDesc);
	    // max stack and max locals automatically computed
	    mv.visitMaxs(0, 0);
		mv.visitEnd();
	}
	
	protected String getGetMethodName(String name){
		return "get" + String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
	}
	protected String getSetMethodName(String name){
		return "set" + String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
	}
}
