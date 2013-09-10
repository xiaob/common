package com.yuan.common.aop;

import java.lang.reflect.Method;

public interface Pointcut {

	public boolean matches(Method method, Object[] args, Object target);
	
}
