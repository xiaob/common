package com.yuan.common.asm;

public abstract class AbstractClassLoader extends ClassLoader {
	
	public AbstractClassLoader(ClassLoader parent){
		super(parent);
	}
	
	protected String getPackageName(String className){
		String packageName = "";
		int index = className.lastIndexOf(".");
		if(index != -1){
			packageName = className.substring(0, index);
		}
		return packageName;
	}
	
	protected Class<?> findClass(String name)throws ClassNotFoundException{
		String packageName = getPackageName(name);
		if(super.getPackage(packageName) == null){
			super.definePackage(packageName, packageName, "1.0", "hx", packageName, "1.0", "hx", null);
		}
		try {
			return this.getParent().loadClass(name);
		} catch (Exception e) {
			byte[] b = loadClassData(name);
	        return defineClass(name, b, 0, b.length);
		}
		
	}
	
	protected abstract byte[] loadClassData(String name) throws ClassNotFoundException;

}
