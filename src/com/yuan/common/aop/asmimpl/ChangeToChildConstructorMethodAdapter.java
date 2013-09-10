package com.yuan.common.aop.asmimpl;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.yuan.common.asm.ClassUtil;


public class ChangeToChildConstructorMethodAdapter extends MethodAdapter {
	private String proxySuperRef;

	public ChangeToChildConstructorMethodAdapter(MethodVisitor mv, String proxySuperRef) {
		super(mv);
		this.proxySuperRef = proxySuperRef;
		
	}
	
	public void visitCode(){
		super.visitCode();
		if(proxySuperRef.equals(Type.getInternalName(Object.class))){
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, proxySuperRef, "<init>", "()V");
		}
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		
		//调用父类的构造函数时
		if (opcode == Opcodes.INVOKESPECIAL && ClassUtil.isConstructor(name)) { 
			owner = proxySuperRef;
		}
		
		super.visitMethodInsn(opcode, owner, name, desc);//改写父类为superClassName
	}

}
