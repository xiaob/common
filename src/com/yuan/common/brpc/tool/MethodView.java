package com.yuan.common.brpc.tool;

public class MethodView {

	private String returnType;
	private String methodName;
	private String parameters;
	
	public MethodView() {
		super();
	}
	
	public MethodView(String returnType, String methodName, String parameters) {
		super();
		this.returnType = returnType;
		this.methodName = methodName;
		this.parameters = parameters;
	}

	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
}
