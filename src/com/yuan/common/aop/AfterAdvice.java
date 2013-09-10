package com.yuan.common.aop;

import java.lang.reflect.Method;

/**
 * 后置增强
 * @author yuan
 *
 */
public interface AfterAdvice extends Advice{
	
	public void afterReturning(Object returnObj, Method method, Object[] args, Object target)throws Throwable;

}
