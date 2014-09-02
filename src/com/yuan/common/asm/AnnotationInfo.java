package com.yuan.common.asm;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

public class AnnotationInfo {

	private Class<? extends Annotation> annotationClass;
	private List<Object> nvList = new ArrayList<Object>();
	
	public AnnotationInfo(Class<? extends Annotation> annotationClass) {
		super();
		this.annotationClass = annotationClass;
	}

	public void put(String name, Object value){
		nvList.add(Object.class.cast(name));
		nvList.add(value);
	}
	
	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	public List<Object> getNvList() {
		return nvList;
	}
	public void setNvList(List<Object> nvList) {
		this.nvList = nvList;
	}
	
	public AnnotationNode toAnnotationNode(){
		AnnotationNode node = new AnnotationNode(Type.getDescriptor(annotationClass));
		if(node.values == null){
			node.values = new ArrayList<Object>();
		}
		node.values.addAll(nvList);
		return node;
	}
	
}
