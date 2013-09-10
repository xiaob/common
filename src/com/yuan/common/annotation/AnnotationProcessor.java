package com.yuan.common.annotation;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//可以用"*"表示支持所有Annotations
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AnnotationProcessor extends AbstractProcessor {

//	private MyScanner myScanner;
	
	public void init(ProcessingEnvironment processingEnv){
		super.init(processingEnv);
//		myScanner = new MyScanner(processingEnv);
	}
	
	private void note(String msg) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
	}

	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		// annotations的值是通过@SupportedAnnotationTypes声明的且目标源代码拥有的所有Annotations
//		if(!roundEnv.processingOver()){
			for (TypeElement te : annotations) {
				note("注解: " + te.toString());
			}
			Set<? extends Element> elements = roundEnv.getRootElements();// 获取源代码的映射对象
			note("根元素数：" + elements.size());
			for (Element e : elements) {
				try {
					if(scanElement(e)){
						return true;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
//				myScanner.scan(e);
			}//for
//		}
		return false;
	}//process
	private boolean scanElement(Element e) throws IOException{
		note("短类名：" + e.getSimpleName());
		note("长类名：" + ((TypeElement)e).getQualifiedName());
		
		PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(e);
		note("包名：" + packageElement.getQualifiedName());
		List<? extends Element> enclosedElems = e.getEnclosedElements();
//		List<? extends VariableElement> ves = ElementFilter.fieldsIn(enclosedElems);
//		if(ves.isEmpty()){
//			String code = "package mycompile; public class TestCopileAPI$Proxy{private String name;}";
//			appendCode(e.getSimpleName(), packageElement, code);
//			return false;
//		}
		
		// 留下方法成员,过滤掉其他成员
		List<? extends ExecutableElement> ees = ElementFilter.methodsIn(enclosedElems);
		for (ExecutableElement ee : ees) {
			List<? extends AnnotationMirror> as = ee.getAnnotationMirrors();// 获取方法的Annotations
			note("方法声明:  " + getMethod(ee));
			note("--as=" + as);
			for (AnnotationMirror am : as) {
				// 获取Annotation的值
				Map<? extends ExecutableElement, ? extends AnnotationValue> map = am
						.getElementValues();
				Set<? extends ExecutableElement> ks = map.keySet();
				for (ExecutableElement k : ks) {// 打印Annotation的每个值
					AnnotationValue av = map.get(k);
					note("----" + ee.getSimpleName() + "."
							+ k.getSimpleName() + "=" + av.getValue());
				}//for
			}//for
		}//for
		return false;
	}
	private String getMethod(ExecutableElement ee){
		String method = "";
		for(Modifier c : ee.getModifiers()){
			method += c.toString()+" ";
		}
		//返回类型 
		method += getShortName(ee.getReturnType())+" " + ee.getSimpleName();
		//输入参数
		method += "(";
		for(VariableElement ve: ee.getParameters()){
			method += getShortName(ve.asType()) + " " + ve.getSimpleName()+",";
		}
		if(method.endsWith(",")){
			method = method.substring(0,method.length()-1);
		}
		method += ")";
		//异常类型
		List<? extends TypeMirror> list = ee.getThrownTypes();
		if((list!=null)&&(!list.isEmpty())){
			method += "throws ";
			for(TypeMirror tm: ee.getThrownTypes()){
				method += tm.toString()+",";
			}
			if(method.endsWith(",")){
				method = method.substring(0,method.length()-1);
			}
		}
		return method;
	}
	
	private String getShortName(TypeMirror typeMirror){
//		TypeKind kind = typeMirror.getKind();
//		if((kind.isPrimitive()) || (kind == TypeKind.VOID)){//基本类型
//			return typeMirror.toString();
//		}else if(kind == TypeKind.ARRAY){
//			TypeMirror componentMirror = ((ArrayType)typeMirror).getComponentType();
//			TypeKind componentKind = componentMirror.getKind();
//			if((componentKind.isPrimitive()) || (componentKind == TypeKind.VOID)){//基本类型
//				return typeMirror.toString();
//			}else{
//				
//			}
//			
//		}else{//对象类型
//			return ClassUtil.getShortClassName(typeMirror.toString());
//		}
		return typeMirror.toString();
	}
	protected void appendCode(CharSequence name, Element originatingElement, String code) throws IOException{
		JavaFileObject javaFileObject = processingEnv.getFiler().createClassFile(name + "$Proxy", originatingElement);
		Writer writer = javaFileObject.openWriter();
		writer.append(code);
		writer.close();
	}

}
