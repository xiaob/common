package com.yuan.common.aop;

/**
 * 异常增强
 * @author yuan
 *
 */
public interface ThrowsAdvice extends Advice{

	public void afterThrowing(Throwable t)throws Throwable;
	
}
