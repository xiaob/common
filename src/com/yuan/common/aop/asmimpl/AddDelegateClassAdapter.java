package com.yuan.common.aop.asmimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.yuan.common.aop.Advice;
import com.yuan.common.aop.Areacut;
import com.yuan.common.asm.ClassUtil;
import com.yuan.common.util.StringUtil;

public class AddDelegateClassAdapter extends ClassAdapter {
	
	private String proxyClassName;
	private List<Class<?>> interfaceClasslist = new ArrayList<Class<?>>();
	private String proxyThisRef;
	private String proxySuperRef;
	private Areacut areacut;
	private String adviceInternalName = Type.getInternalName(Advice.class);
	
	public AddDelegateClassAdapter(ClassVisitor cv, String proxyClassName, List<Class<?>> interfaceClasslist, Areacut areacut) {
		//Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
		//负责改写后代码的输出
		super(cv);
		this.proxyClassName = proxyClassName;
		if(interfaceClasslist != null){
			this.interfaceClasslist.addAll(interfaceClasslist);
		}
		this.areacut = areacut;
	}
	
	public String getRealClassName(){
		return proxyThisRef.replaceAll("\\/", ".");
	}
	
	private String[] appendInterfaces(String[] interfaces){
		List<String> ifList = new ArrayList<String>();
		if(interfaces != null){
			ifList.addAll(Arrays.asList(interfaces));
		}
		
		String[] ifs = new String[ifList.size() + interfaceClasslist.size()];
		for(int i=0; i<ifList.size(); i++){
			ifs[i] = ifList.get(i);
		}
		for(int j=ifList.size(); j<ifs.length; j++){
			ifs[j] = interfaceClasslist.get(j - ifList.size()).getName();
			ifs[j] = ifs[j].replaceAll("\\.", "\\/");
		}
		
		return ifs;
	}
	
	private void makeProxyThisRef(String name){
		if(name.startsWith("java") || name.startsWith("javax")){
			String internalPackageName = ClassUtil.getInternalPackageName(AddDelegateClassAdapter.class);
			String shortName = ClassUtil.getShortName(name);
			proxyThisRef = internalPackageName + shortName + "$EnhancedByASM";  //改变类命名
			if(StringUtil.hasText(proxyClassName)){
				proxyThisRef = internalPackageName + proxyClassName;
			}
		}else{
			proxyThisRef = name + "$EnhancedByASM";  //改变类命名
			if(StringUtil.hasText(proxyClassName)){
				proxyThisRef = name.substring(0, name.lastIndexOf("/") + 1) + proxyClassName;
			}
		}
		
	}
	
	private void weaveAdvice(){
		
		//添加增强委托对象字段
        cv.visitField(Opcodes.ACC_PRIVATE, "adviceProxy", "L"+adviceInternalName+";", null, null).visitEnd();
        
     // 生成 setAdvice 方法
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "setAdvice", "(Ljava/lang/Object;)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitTypeInsn(Opcodes.CHECKCAST, adviceInternalName); //强制类型转换
        mv.visitFieldInsn(Opcodes.PUTFIELD, proxyThisRef, "adviceProxy", "L"+adviceInternalName+";");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
	}
	
	private boolean hasAdvice(){
		return (areacut != null) && (areacut.getAdvice() != null);
	}
	
	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		makeProxyThisRef(name);
		
		proxySuperRef = name; //改变父类，这里是”Account”
		
		super.visit(version, Opcodes.ACC_PUBLIC, proxyThisRef, signature, proxySuperRef, appendInterfaces(interfaces));
		
		if(hasAdvice()){
			weaveAdvice();
		}
	}
	
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, final String signature, final String[] exceptions) {
		if(!ClassUtil.isConstructor(name)){
			/*
			if(hasAdvice() && areacut.getPointcut().isMatch(name)){
				String[] es = new String[]{"java/lang/Throwable"}; 
				MethodVisitor mv = cv.visitMethod(access, name, desc, signature, es);
				
				return new WeaveAdviceMethodAdapter(mv, proxyThisRef, adviceInternalName);
			}
			*/
			return null;
		}
		
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		
		return new ChangeToChildConstructorMethodAdapter(mv, proxySuperRef);
	}

	public void visitEnd(){
		for(Class<?> c : interfaceClasslist){
			addInterface(c);
		}
	}
	
	private void addInterface(Class<?> interfaceClass){
		
		AddInterfacesClassAdapter ca = new AddInterfacesClassAdapter(cv, interfaceClass, proxyThisRef, adviceInternalName, areacut);
		try {
			ClassReader classReader = new ClassReader(interfaceClass.getName());
			classReader.accept(ca, ClassReader.SKIP_DEBUG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
