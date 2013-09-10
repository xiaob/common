package com.yuan.common.annotation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner6;
import javax.tools.Diagnostic;

public class MyScanner extends ElementScanner6<Void, Void> {

	private ProcessingEnvironment processingEnv;
	
	public MyScanner(ProcessingEnvironment processingEnv) {
		super();
		this.processingEnv = processingEnv;
	}

	private void note(String msg) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
	}
	
	public Void visitPackage(PackageElement e, Void p){
		note("包名：" + e.getQualifiedName());
		super.visitPackage(e, p);
		return null;
	}
	public Void visitExecutable(ExecutableElement e, Void p) {
		note("方法名：" + e.getSimpleName());
		super.visitExecutable(e, p);
		return null;
	}
	public Void visitVariable(VariableElement e, Void p) {
		note("字段名：" + e.getSimpleName());
		super.visitVariable(e, p);
		return null;
	}
	 
}
