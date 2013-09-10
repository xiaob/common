package com.yuan.common.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SpringParser {

	
	public static void main(String[] args)throws Exception {
		SAXReader reader = new SAXReader(); 
		setEntityResolver(reader); //此句必须有, 不然不能处理有DTD验证的xml文档
		
		
        Document document = reader.read(new File("e:/tmp/applicationContext.xml"));
        Element root = document.getRootElement();
        System.out.println(root.getName());
        Element datasource = root.element("bean");
        System.out.println(datasource.attributeValue("class"));
	}
	
	//处理DTD问题
	private static void setEntityResolver(SAXReader reader) {
		reader.setEntityResolver(new EntityResolver() {

			String emptyDtd = "";

			ByteArrayInputStream bytels = new ByteArrayInputStream(emptyDtd
					.getBytes());

			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(bytels);
			}
		});
	}//setEntityResolver

}
