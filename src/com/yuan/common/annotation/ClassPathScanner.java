package com.yuan.common.annotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class ClassPathScanner {

	private boolean excludeInner = true;
	private boolean checkInOrEx = true;
	private List<String> classFilters = null;

	public ClassPathScanner() {
	}

	public ClassPathScanner(Boolean excludeInner, Boolean checkInOrEx, List<String> classFilters) {
		this.excludeInner = excludeInner;
		this.checkInOrEx = checkInOrEx;
		this.classFilters = classFilters;
	}

	public boolean isExcludeInner() {
		return excludeInner;
	}

	public void setExcludeInner(boolean excludeInner) {
		this.excludeInner = excludeInner;
	}

	public boolean isCheckInOrEx() {
		return checkInOrEx;
	}

	public void setCheckInOrEx(boolean checkInOrEx) {
		this.checkInOrEx = checkInOrEx;
	}

	public List<String> getClassFilters() {
		return classFilters;
	}

	public void setClassFilters(List<String> classFilters) {
		this.classFilters = classFilters;
	}

	public Set<Class<?>> getPackageAllClasses(String basePackage, boolean recursive) throws IOException,
			ClassNotFoundException {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageName = basePackage;
		if (packageName.endsWith(".")) {
			packageName = packageName.substring(0, packageName.lastIndexOf('.'));
		}
		String package2Path = packageName.replace('.', '/');
		Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(package2Path);
		while (dirs.hasMoreElements()) {
			URL url = dirs.nextElement();
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
				doScanPackageClassesByFile(classes, packageName, filePath, recursive);
			} else if ("jar".equals(protocol)) {
				doScanPackageClassesByJar(packageName, url, recursive, classes);
			}
		}
		return classes;

	}

	private void doScanPackageClassesByJar(String basePackage, URL url, final boolean recursive, Set<Class<?>> classes)
			throws IOException, ClassNotFoundException {
		String packageName = basePackage;
		String package2Path = packageName.replace('.', '/');
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			if (!name.startsWith(package2Path) || entry.isDirectory()) {
				continue;
			}
			// 判断是否递归搜索子包
			if (!recursive && name.lastIndexOf('/') != package2Path.length()) {
				continue;
			}
			// 判断是否过滤 inner class
			if (this.excludeInner && name.indexOf('$') != -1) {
				continue;
			}
			String classSimpleName = name.substring(name.lastIndexOf('/') + 1);
			// 判定是否符合过滤条件
			if (this.filterClassName(classSimpleName)) {
				String className = name.replace('/', '.');
				className = className.substring(0, className.length() - 6);
				classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
			}
		}
	}

	private void doScanPackageClassesByFile(Set<Class<?>> classes, String packageName, String packagePath,
			boolean recursive) throws ClassNotFoundException {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		final boolean fileRecursive = recursive;
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义文件过滤规则
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return fileRecursive;
				}
				String filename = file.getName();
				if (excludeInner && filename.indexOf('$') != -1) {
					return false;
				}
				return filterClassName(filename);
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				doScanPackageClassesByFile(classes, packageName + "." + file.getName(), file.getAbsolutePath(),
						recursive);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
			}
		}
	}

	private boolean filterClassName(String className) {
		if (!className.endsWith(".class")) {
			return false;
		}
		if (null == this.classFilters || this.classFilters.isEmpty()) {
			return true;
		}
		String tmpName = className.substring(0, className.length() - 6);
		boolean flag = false;
		for (String str : classFilters) {
			String tmpreg = "^" + str.replace("*", ".*") + "$";
			Pattern p = Pattern.compile(tmpreg);
			if (p.matcher(tmpName).find()) {
				flag = true;
				break;
			}
		}
		return (checkInOrEx && flag) || (!checkInOrEx && !flag);
	}

}
