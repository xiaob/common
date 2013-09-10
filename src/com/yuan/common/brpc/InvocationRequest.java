package com.yuan.common.brpc;

import java.io.Serializable;
import java.lang.reflect.Method;

public class InvocationRequest implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private String className;
	
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public InvocationRequest(String className, Method method, Object[] parameters) {
    	this.className = className;
        this.methodName = method.getName();
        this.parameterTypes = method.getParameterTypes();
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
