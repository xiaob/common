package com.yuan.common.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Op {
	
	public static void print(MethodVisitor mv, String str){
		mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
        mv.visitLdcInsn(str);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V");
	}
	
	public static boolean isReturnCode(int opcode){
		switch(opcode){
		case Opcodes.IRETURN:
		case Opcodes.ARETURN:
		case Opcodes.DRETURN:
		case Opcodes.FRETURN:
		case Opcodes.LRETURN:
		case Opcodes.RETURN:
			return true;
		}
		return false;
	}
	
	public static void returns(MethodVisitor mv, String desc){
		int returnCode = Opcodes.RETURN;
		switch(Type.getReturnType(desc).getSort()){
		case Type.BOOLEAN : 
		case Type.BYTE : 
		case Type.CHAR :
		case Type.INT :
		case Type.SHORT :
			returnCode = Opcodes.IRETURN;
			break;
		case Type.ARRAY :
		case Type.OBJECT :
			returnCode = Opcodes.ARETURN;
			break;
		case Type.DOUBLE :
			returnCode = Opcodes.DRETURN;
			break;
		case Type.FLOAT :
			returnCode = Opcodes.FRETURN;
			break;
		case Type.LONG :
			returnCode = Opcodes.LRETURN;
			break;
		case Type.VOID :
			returnCode = Opcodes.RETURN;
			break;
		}
		mv.visitInsn(returnCode);
	}
	
	public static void pushConstant(MethodVisitor mv, Object value){
		mv.visitLdcInsn(value);
	}
	
	public static void pushInputParam(MethodVisitor mv, String desc){
		Type[] inputParamType = Type.getArgumentTypes(desc);
//		System.out.println("inputParamType = " + inputParamType.length);
		
		int index = 0;
		for(int i=0; i<inputParamType.length; i++){
			index += 1;
			int loadCode = Opcodes.ALOAD;
			switch(inputParamType[i].getSort()){
			case Type.BOOLEAN : 
			case Type.BYTE : 
			case Type.CHAR :
			case Type.INT :
			case Type.SHORT :
				loadCode = Opcodes.ILOAD;
				break;
			case Type.ARRAY :
			case Type.OBJECT :
				loadCode = Opcodes.ALOAD;
				break;
			case Type.DOUBLE :
				loadCode = Opcodes.DLOAD;
				break;
			case Type.FLOAT :
				loadCode = Opcodes.FLOAD;
				break;
			case Type.LONG :
				loadCode = Opcodes.LLOAD;
				break; 
			}
			mv.visitVarInsn(loadCode, index);
			if((inputParamType[i].getSort() == Type.DOUBLE) || (inputParamType[i].getSort() == Type.LONG)){
				index ++ ;
			}
		}//for
	}
	
}
