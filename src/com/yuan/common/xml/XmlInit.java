package com.yuan.common.xml;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标注方法是初始化方法，对象被构建后该方法会被执行
 * @author <a href="mailto:cihang.yuan@happyelements.com">cihang.yuan</a>
 * @version 1.0 2014年4月14日
 * @since 1.0
 */
@Retention(RUNTIME) @Target({METHOD})
public @interface XmlInit {
	
}
