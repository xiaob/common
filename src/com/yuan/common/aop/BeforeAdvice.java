package com.yuan.common.aop;

import java.lang.reflect.Method;

/**
 * 前置增强
 * @author yuan
 *
 */
public interface BeforeAdvice extends Advice{

	public void before(Method method, Object[] args, Object target)throws Throwable;
	
}
