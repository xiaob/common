package com.yuan.common.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.lang.model.element.Modifier;
@MyClass
@Resource
public class Testing {
	@MyField
	@Resource
	private String name;
	@MyMethod
	public void go(){
		
	}
	@ToBeTested(group="A")
	public void m1(){
		
	}
	@ToBeTested(group="B",owner="QQ")
	public void m2(){
		
	}
	@PostConstruct//Common Annotation里面的一个Annotation
	public void m3(){
		
	}
	
	public static void main(String[] args) {
		for(Modifier c : Modifier.values())
	        System.out.println(c);
	}

}
