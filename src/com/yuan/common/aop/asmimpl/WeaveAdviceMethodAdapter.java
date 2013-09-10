package com.yuan.common.aop.asmimpl;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.yuan.common.asm.Op;

public class WeaveAdviceMethodAdapter extends MethodAdapter {
	
	private String proxyThisRef;
	private String adviceInternalName;
	private Label l0 = new Label();
	private Label l1 = new Label();
	private Label l2 = new Label();
	
	public WeaveAdviceMethodAdapter(MethodVisitor mv, String proxyThisRef, String adviceInternalName){
		super(mv);
		this.proxyThisRef = proxyThisRef;
		this.adviceInternalName = adviceInternalName;
	}
	
	public void visitCode(){
		super.visitCode();
		
		mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Throwable");
		mv.visitLabel(l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, proxyThisRef, "adviceProxy", "L"+adviceInternalName+";");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, adviceInternalName, "beforeMethod", "()V");
	}
	
	public void visitInsn(int opcode){
		if(Op.isReturnCode(opcode)){
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
		
		super.visitInsn(opcode);
	}
	
	public void visitEnd(){
		super.visitEnd();
	}

}
