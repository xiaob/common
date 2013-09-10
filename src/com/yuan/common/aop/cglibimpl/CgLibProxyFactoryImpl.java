package com.yuan.common.aop.cglibimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.yuan.common.aop.AfterAdvice;
import com.yuan.common.aop.Areacut;
import com.yuan.common.aop.AroundAdvice;
import com.yuan.common.aop.BeforeAdvice;
import com.yuan.common.aop.MethodInvocation;
import com.yuan.common.aop.ProxyFactory;
import com.yuan.common.aop.ThrowsAdvice;
import com.yuan.common.asm.ClassUtil;

public class CgLibProxyFactoryImpl implements MethodInterceptor, ProxyFactory{
	
	private Class<?> superClass;
	private List<Class<?>> interfaceList = new ArrayList<Class<?>>();
	private Map<Class<?>, Object> interfaceDelegateObjectMap = new HashMap<Class<?>, Object>();
	private List<Areacut> beforeAreacutList = new ArrayList<Areacut>();
	private List<Areacut> afterAreacutList = new ArrayList<Areacut>();
	private List<Areacut> AroundAreacutList = new ArrayList<Areacut>();
	private List<Areacut> throwsAreacutList = new ArrayList<Areacut>();
	
	public void setSuperClass(Class<?> superClass){
		this.superClass = superClass;
	}
	
	public void addDelegate(Class<?> interfaceClass, Object interfaceDelegateObject){
		interfaceList.add(interfaceClass);
		interfaceDelegateObjectMap.put(interfaceClass, interfaceDelegateObject);
	}
	
	public void addAreacut(Areacut areacut){
		if(areacut.getAdvice() instanceof BeforeAdvice){
			beforeAreacutList.add(areacut);
		}
		if(areacut.getAdvice() instanceof AfterAdvice){
			afterAreacutList.add(areacut);
		}
		if(areacut.getAdvice() instanceof AroundAdvice){
			AroundAreacutList.add(areacut);
		}
		if(areacut.getAdvice() instanceof ThrowsAdvice){
			throwsAreacutList.add(areacut);
		}
		
	}
	
	private Class<?>[] getInterfaces(){
		Class<?>[] cs = new Class[interfaceList.size()];
		for(int i=0; i<interfaceList.size(); i++){
			cs[i] = interfaceList.get(i);
		}
		return cs;
	}
	
	private boolean isSameMethod(Method m1, Method m2){
		if(!m1.getName().equals(m2.getName())){
			return false;
		}
		Class<?>[] paramTypes1 = m1.getParameterTypes();
		Class<?>[] paramTypes2 = m2.getParameterTypes();
		if(paramTypes1.length != paramTypes2.length){
			return false;
		}
		
		for(int i=0;i<paramTypes1.length; i++){
			if(!paramTypes1[i].isAssignableFrom(paramTypes2[1])){
				return false;
			}
		}
		return true;
	}
	
	private Object getDelegateObject(Method method){
		for(Class<?> clazz : interfaceList){
			Method[] mm = clazz.getMethods();
			for(Method m : mm){
				if(isSameMethod(method, m)){
					return interfaceDelegateObjectMap.get(clazz);
				}
			}
		}
		return null;
	}
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object result = null;
		Object delegateObject = getDelegateObject(method);
		Object target = null;
		if(delegateObject == null){
			target = obj;
		}else{
			target = delegateObject;
		}
		try {
			beforeWeave(method, args, target);
			for(Areacut areacut : AroundAreacutList){
				if(areacut.getPointcut().matches(method, args, target)){
					Object adviceResult = null;
					try {
						if(delegateObject == null){
							adviceResult = ((AroundAdvice)areacut.getAdvice()).around(new MethodInvocation(proxy, method, args, target));
						}else{
							adviceResult = ((AroundAdvice)areacut.getAdvice()).around(new MethodInvocation(null, method, args, target));
						}
						
						afterWeave(adviceResult, method, args, target);
					} catch (Exception e) {
						exceptionWeave(method, args, target, e);
						return adviceResult;
					}
					return adviceResult;
				}//if
			}//for
			
			if(delegateObject == null){
				result =  proxy.invokeSuper(obj, args);
			}else{
				result =  method.invoke(delegateObject, args);
			}
			afterWeave(result, method, args, target);
		} catch (Exception e) {
			exceptionWeave(method, args, target, e);
		}
		return result;
	}
	private void beforeWeave(Method method, Object[] args, Object target)throws Throwable{
		for(Areacut areacut : beforeAreacutList){
			if(areacut.getPointcut().matches(method, args, target)){
				((BeforeAdvice)areacut.getAdvice()).before(method, args, target);
			}
		}
	}
	private void afterWeave(Object returnObj, Method method, Object[] args, Object target)throws Throwable{
		for(Areacut areacut : afterAreacutList){
			if(areacut.getPointcut().matches(method, args, target)){
				((AfterAdvice)areacut.getAdvice()).afterReturning(returnObj, method, args, target);
			}
		}
	}
	private void exceptionWeave(Method method, Object[] args, Object target, Throwable t)throws Throwable{
		for(Areacut areacut : throwsAreacutList){
			if(areacut.getPointcut().matches(method, args, target)){
				((ThrowsAdvice)areacut.getAdvice()).afterThrowing(t);
			}
		}
	}
	
	public Object getProxy(){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(superClass);
		enhancer.setInterfaces(getInterfaces());
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	public Class<?> createProxyClass(){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(superClass);
		enhancer.setInterfaces(getInterfaces());
		enhancer.setCallbackType(this.getClass());
		return enhancer.createClass();
	}
	
	public void writeToFile(String dir)throws IOException{
		Class<?> proxyClass = createProxyClass();
		String path = dir + ClassUtil.getByteCodeName(proxyClass) + ".class";
		if(!new File(path).getParentFile().exists()){
			new File(path).getParentFile().mkdirs();
		}
		byte[] byteCode = ClassUtil.getByteCode(proxyClass);
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(byteCode);
		fos.close();
	}
	
	public static void main(String args[]){
//		ProxyFactory pf = new ProxyFactory();
//		pf.setSuperClass(A.class);
//		pf.addDelegate(IMan.class, new Man());
//		Object proxy = pf.getProxy();
//		A a = (A)proxy;
//		a.a();
		
//		IMan m = (IMan)proxy;
//		m.go();
	}

}
