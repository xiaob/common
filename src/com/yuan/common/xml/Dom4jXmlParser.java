package com.yuan.common.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jXmlParser {
	
	private static Map<String, Property> confMap = new HashMap<String, Property>();
	
	static{
		
		List<Property> list = null;
		try {
			list = load(Dom4jXmlParser.class.getResourceAsStream("/url-default.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Property property:list){
			confMap.put(property.getName(), property);
		}//for
	}
	
	public static List<Property> load(InputStream is)throws Exception{
		List<Property> list = new ArrayList<Property>();
		
		SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        Element root = document.getRootElement();
        
        Iterator<?> it = root.elementIterator("property");
        while(it.hasNext()){
        	Element e = (Element)it.next();
        	list.add(getProperty(e));
        }
        is.close();
        
		return list;
	}
	
	private static Property getProperty(Element e){
		Property property = new Property();
		property.setName(e.elementTextTrim("name"));
		property.setValue(e.elementTextTrim("value"));
		property.setDescription(e.elementTextTrim("description"));
		
		return property;
	}
	
	public static String getValue(String name){
		if(!confMap.containsKey(name)){
			return null;
		}
		Property property = confMap.get(name);
		return property.getValue();
	}
	
	public static String getDescription(String name){
		if(!confMap.containsKey(name)){
			return null;
		}
		Property property = confMap.get(name);
		return property.getDescription();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Dom4jXmlParser.getValue("host"));
		System.out.println(Dom4jXmlParser.getDescription("host"));
	}
	
	/**
	 * 带缩进和换行写入xml文件, 用于添加虚拟页面
	 * @param xmlFile String
	 * @param document Document
	 * @throws TaeException
	 */
	public static void writeToXml(final String xmlFile, Document document)throws Exception{
		//设置显示
		OutputFormat opf = new OutputFormat(" 	", true);
		opf.setEncoding("UTF-8");

		//输出
		Writer writer = new OutputStreamWriter(new FileOutputStream(xmlFile), "utf8"); 
		XMLWriter output = new XMLWriter(writer, opf);
		output.write(document);
		output.close();
		
	}
	
	/**
	 * 不带格式写入xml文件, 用于修改,删除虚拟页面
	 * @param xmlFile
	 * @param document
	 * @throws TaeException
	 */
	public static void writeToXml2(final String xmlFile, Document document)throws Exception{
		//输出
		Writer writer = new OutputStreamWriter(new FileOutputStream(xmlFile), "utf8"); 
		XMLWriter output = new XMLWriter(writer);
		output.write(document);
		output.close();
		
	}
	
	public static Element getRootElement(String xmlFile)throws Exception{
		SAXReader reader = new SAXReader();
        Document document = null;
		try {
			document = reader.read(new FileInputStream(xmlFile));
		} catch (FileNotFoundException e) {
			throw new Exception("配置文件" + xmlFile + "没有找到. " + e.getMessage());
		} catch (DocumentException e) {
			throw new Exception(e.getMessage());
		}
        Element root = document.getRootElement();
        return root;
	}
	
	public static Element createSimpleElement(String elementName, String elementText){
		Element simpleElement = DocumentHelper.createElement(elementName);
		simpleElement.setText(elementText);
		return simpleElement;
	}
}
