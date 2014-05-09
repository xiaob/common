package com.yuan.common.xml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * XML与Java对象绑定
 * @author <a href="mailto:cihang.yuan@happyelements.com">cihang.yuan</a>
 * @version 1.0 2014年4月14日
 * @since 1.0
 */
public class Jaxb {
	
	public static <T extends Object> T parseFromString(Class<T> clazz, String xmlStr){
		return parse(clazz, new StringReader(xmlStr));
	}
	public static <T extends Object> T parse(Class<T> clazz, final String xmlFile){
		return parse(clazz, xmlFile, false);
	}
	
	public static <T extends Object> T parse(Class<T> clazz, final String xmlFile, final boolean isValidate){
		File f = new File(xmlFile);
		if(!f.exists()){
			return null;
		}
		if(isValidate && !validator(f)){
			throw new RuntimeException("file "+xmlFile+" valid failed");
		}
		T obj = null;
		try {
			obj = parse(clazz, new FileReader(f));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return obj;
	}
	
	public static <T extends Object> T parse(Class<T> clazz, InputStream is){
		return parse(clazz, is, "UTF-8");
	}
	public static <T extends Object> T parse(Class<T> clazz, InputStream is, final String encoding){
		T obj = null;
		try {
			obj = parse(clazz, new InputStreamReader(is, encoding));
			is.close();
			execInit(clazz, obj);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return obj;
	}
	private static void execInit(Class<?> clazz, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method[] methods = clazz.getMethods();
		if(methods != null){
			for(Method method : methods){
				if(method.getAnnotation(XmlInit.class) != null){
					method.invoke(obj);
					return ;
				}
			}
		}
	}
	public static <T extends Object> T parse(Class<T> clazz, Reader reader){
		T obj = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller um = context.createUnmarshaller();
			obj = clazz.cast(um.unmarshal(reader));
			reader.close();
			execInit(clazz, obj);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return obj;
	}
	
	/**
	 * 验证文档是否有效
	 * @param f File
	 * @return
	 */
	public static boolean validator(final File f){
		//加载一个模式工厂
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			//编译源文件中的模式
			Schema schema = schemaFactory.newSchema();
			//用编译后的模式创建一个验证程序
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new ForgivingErrorHandler());
			//为需要验证的文档创建Source对象。StreamSource通常最简单
			Source source = new StreamSource(f);
			//验证输入的源文档。如果文档无效，则抛出SAXException.否则什么也不显示
			validator.validate(source);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	public static void write(Object obj , final String xmlFile){
		File f = new File(xmlFile);
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); //指定已编组 XML 数据中的输出编码,默认为UTF-8
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //使用换行和缩排对已编组 XML 数据进行格式化,默认为false
			FileWriter fw = new FileWriter(f);
			m.marshal(obj, fw);
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static void main(String args[]){
		write(new String("111"), "e:/test/jaxb.xml");
	}

}


