package com.yuan.common.aop;

/**
 * 环绕增强
 * @author yuan
 *
 */
public interface AroundAdvice extends Advice{

	public Object around(MethodInvocation invocation)throws Throwable;
	
}
