package com.yuan.common.xml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

/**
 * 实用XPATH解析xml的工具
 * xpath表达式用法: /图书馆/书/@名称 , /configuration/property[name = 'host']/value
 * 
 * @author yuan
 *
 */
public class XpathTool {
	
	private String xmlFile;
	private String encoding;
	
	public XpathTool(final String xmlFile){
		this(xmlFile, "UTF-8");
	}
	
	public XpathTool(final String xmlFile, final String encoding){
		this.xmlFile = xmlFile;
		this.encoding = encoding;
	}
	
	public String compute(String expression, Map<String, String> namespaceMap) throws XmlException{
		String value = "";
		InputStreamReader isr = null;
		try {
		    isr = new InputStreamReader(XpathTool.class.getResourceAsStream(xmlFile),encoding);
			InputSource source = new InputSource(isr);
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			if(namespaceMap != null){
				xpath.setNamespaceContext(new DefaultNamespaceContext(namespaceMap));
				System.out.println("设置名称空间...");
			}
			
			value = xpath.evaluate(expression, source);
		}  catch (UnsupportedEncodingException e) {
			throw new XmlException(e.getMessage(), e);
		} catch (XPathExpressionException e) {
			throw new XmlException(e.getMessage(), e);
		} finally{
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					throw new XmlException(e.getMessage(), e);
				}
			}//if
		}
		
		return value;
	}
	
	public static void main(String args[]){
		XpathTool xpath = new XpathTool("/url-default.xml");
		System.out.println(xpath.compute("/configuration/property[name = 'host']/value", null));
	}
	
}
