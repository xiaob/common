package com.yuan.common.aop;


/**
 * 切面
 * @author yuan
 *
 */
public class Areacut {
	
	private Pointcut pointcut = new PointcutImpl(); //切点
	private Advice advice; //增强
	
	public Areacut(){
		
	}
	
	public Areacut(Pointcut pointcut, Advice advice){
		this.pointcut = pointcut;
		this.advice = advice;
	}

	public Pointcut getPointcut() {
		return pointcut;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}

	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}
	
}
