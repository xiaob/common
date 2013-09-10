package com.yuan.common.aop.asmimpl;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.yuan.common.aop.Areacut;
import com.yuan.common.asm.ClassUtil;
import com.yuan.common.asm.Op;


public class AddInterfacesClassAdapter extends ClassAdapter {

//	private Class<?> interfaceClass;
	private String proxyThisRef;
	
//	private String interfaceByteCodeName;
	private String interfaceDelegateObjectName;
	private String interfaceShortName;
	private String interfaceTypeName;
	private String interfaceType;
	private String adviceInternalName;
	private Areacut areacut;
	private Label l0 = new Label();
	private Label l1 = new Label();
	private Label l2 = new Label();

	public AddInterfacesClassAdapter(ClassVisitor cv, Class<?> interfaceClass, String proxyThisRef, String adviceInternalName, Areacut areacut) {
		super(cv);
		
//		this.interfaceClass = interfaceClass;
		this.proxyThisRef = proxyThisRef;
		
		this.interfaceShortName = ClassUtil.getShortName(interfaceClass);
//		this.interfaceByteCodeName = ClassUtil.getByteCodeName(interfaceClass);
		this.interfaceDelegateObjectName = ClassUtil.formatVariableName(interfaceShortName + "Proxy");
		this.interfaceTypeName = Type.getInternalName(interfaceClass);
		this.interfaceType = "L" + interfaceTypeName + ";";
		this.adviceInternalName = adviceInternalName;
		this.areacut = areacut;
	}
	
	protected boolean hasAdvice(){
		return (areacut != null) && (areacut.getAdvice() != null);
	}
	
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		
		//添加委托对象字段
        cv.visitField(Opcodes.ACC_PRIVATE, interfaceDelegateObjectName, interfaceType, null, null).visitEnd();
        
     // 生成 setTarget 方法
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "set" + interfaceShortName,
        		"(Ljava/lang/Object;)V", null, null);
//            "(L"+interfaceByteCodeName+";)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitTypeInsn(Opcodes.CHECKCAST, interfaceTypeName); //强制类型转换
        mv.visitFieldInsn(Opcodes.PUTFIELD, proxyThisRef, interfaceDelegateObjectName, interfaceType);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
	}
	
	public void visitSource(String source, String debug){

	}
	
	public void visitEnd(){
		
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		
		String[] es = exceptions;
//		if(hasAdvice() && areacut.getPointcut().isMatch(name)){
//			es = new String[]{"java/lang/Throwable"};
//		}
		MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, name, desc, signature, es);
		mv.visitCode();
		
//		if(hasAdvice() && areacut.getPointcut().isMatch(name)){
//			weaveBefore(mv);
//		}
		
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, proxyThisRef, interfaceDelegateObjectName, interfaceType); //this.delegateObjectProxy
        Op.pushInputParam(mv, desc);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, interfaceTypeName, name, desc);
        
//        if(hasAdvice() && areacut.getPointcut().isMatch(name)){
//        	weaveAfter(mv);
//        }
        
        Op.returns(mv, desc);
     // max stack and max locals automatically computed
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        return mv;
	}
	
	protected void weaveBefore(MethodVisitor mv){
		mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Throwable");
		mv.visitLabel(l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, proxyThisRef, "adviceProxy", "L"+adviceInternalName+";");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, adviceInternalName, "beforeMethod", "()V");
	}
	
	protected void weaveAfter(MethodVisitor mv){
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, proxyThisRef, "adviceProxy", "L"+adviceInternalName+";");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, adviceInternalName, "afterMethod", "()V");
		mv.visitLabel(l1);
		Label l3 = new Label();
		mv.visitJumpInsn(Opcodes.GOTO, l3);
		mv.visitLabel(l2);
		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
		mv.visitVarInsn(Opcodes.ASTORE, 1);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, proxyThisRef, "adviceProxy", "L"+adviceInternalName+";");
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, adviceInternalName, "exceptionMethod", "(Ljava/lang/Throwable;)V");
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	}
	
}

