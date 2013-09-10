package com.yuan.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToBeTested {
	
	String owner() default "Chinajash";

	String group();
	
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Test1{
	
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test2{
	String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Test3{
	int size();
	String info();
	String name() default "wang";
}
