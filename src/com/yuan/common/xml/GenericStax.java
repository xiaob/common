package com.yuan.common.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class GenericStax {
	// Namespaces
//	private static final String GARDENING = "http://com.bdaum.gardening";
//	private static final String XHTML = "http://www.w3.org/1999/xhtml";
	private String xmlFile = "";
	private String encoding = "";
	
	public GenericStax(String xmlFile){
		this.xmlFile = xmlFile;
		this.encoding = "UTF-8";
	}
	public GenericStax(String xmlFile, String encoding){
		this.xmlFile = xmlFile;
		this.encoding = encoding;
	}
	
	public static void main(String[] args)throws Exception{
		final String file = "d:/tmp/web.xml";
		//System.out.println(getSex(file));
		new GenericStax(file).parser();
		//System.out.println("版本: "+GenericStax.getXmlVersion(file));
		//System.out.println("编码: "+GenericStax.getXmlEncoding(file));
		//System.out.println("独立吗: "+GenericStax.isStandalone(file));
		//testWrite();
	}
	
	public static void testWrite()throws XMLStreamException {
		final String XHTML_NS = "http://www.w3.org/1999/xhtml";
		final QName HTML_TAG = new QName(XHTML_NS, "html");
		final QName HEAD_TAG = new QName(XHTML_NS, "head");
		final QName TITLE_TAG = new QName(XHTML_NS, "title");
		final QName BODY_TAG = new QName(XHTML_NS, "body");

		XMLOutputFactory f = XMLOutputFactory.newInstance();
		XMLEventWriter w = f.createXMLEventWriter(System.out);
		XMLEventFactory ef = XMLEventFactory.newInstance();
		try {
			w.add(ef.createStartDocument());
			w.add(ef.createIgnorableSpace("\n"));
			w.add(ef.createDTD("<!DOCTYPE html "
									+ "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
									+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"));
			w.add(ef.createIgnorableSpace("\n"));
			w.add(ef.createStartElement(HTML_TAG, null, null));
			w.add(ef.createNamespace(XHTML_NS));
			w.add(ef.createAttribute("lang", "en"));
			w.add(ef.createIgnorableSpace("\n"));
			w.add(ef.createStartElement(HEAD_TAG, null, null));
			w.add(ef.createStartElement(TITLE_TAG, null, null));
			w.add(ef.createCharacters("Test"));
			w.add(ef.createEndElement(TITLE_TAG, null));
			w.add(ef.createEndElement(HEAD_TAG, null));
			w.add(ef.createIgnorableSpace("\n"));
			w.add(ef.createStartElement(BODY_TAG, null, null));
			w.add(ef.createCharacters("This is a test."));
			w.add(ef.createEndElement(BODY_TAG, null));
			w.add(ef.createEndElement(HTML_TAG, null));
			w.add(ef.createEndDocument());
		} finally {
			w.close();
		}
	}
	
	public void parser()throws Exception {
		Reader isr = new InputStreamReader(new FileInputStream(this.xmlFile),this.encoding);
		//FileReader fr = new FileReader(this.xmlFile);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader = inputFactory.createXMLEventReader(isr);
		try {
			while (reader.hasNext()) {
				XMLEvent e = reader.nextEvent();
				dispatcherEvent(e);
			}
		} finally {
			reader.close();
		}
	}
	
	private void dispatcherEvent(XMLEvent event) {
		
		switch (event.getEventType()) {
		case XMLStreamConstants.START_ELEMENT:
			processStartElement(event.asStartElement());
			break;
		case XMLStreamConstants.END_ELEMENT:
			processEndElement(event.asEndElement());
			break;
		case XMLStreamConstants.CHARACTERS:
			processCharacters(event.asCharacters());
			break;
		case XMLStreamConstants.COMMENT:
			processComment((Comment)event);
		}
	}
	
	protected void processStartElement(StartElement startElement){
		System.out.println(startElement.getName());
		Iterator<?> it = startElement.getAttributes();
		Attribute  attribute = null;
		while(it.hasNext()){
			attribute = (Attribute)it.next();
			System.out.println(attribute.getName()+"="+attribute.getValue());
		}
	}
	
	protected void processEndElement(EndElement endElement){
		
	}
	
	protected void processCharacters(Characters characters){
		
		if(characters.isCData()){//CDATA部分
			
		}else if(characters.isIgnorableWhiteSpace()){//如果是可忽略的空白
			
		}else if(characters.isWhiteSpace()){//如果文本是全部由空白字符组成（不一定是可忽略的空白）
			
		}else{
			System.out.println(characters.getData());
		}//if
		
	}
	
	protected void processComment(Comment comment){
		System.out.println(comment.getText());
	}
	
	public static String getAttributeValue(String xmlFile, String elementName, String attributeName){
		 String value = null;
		 QName qElement = new QName(elementName);
		 QName qAttribute = new QName(attributeName);
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 XMLEventReader  reader = null;
		 try {
			reader = inputFactory.createXMLEventReader(new FileReader(xmlFile));
			 while (reader.hasNext()){
			      XMLEvent event = reader.nextEvent();
			      if (event.isStartElement()){
			           StartElement element = event.asStartElement();
			           if (element.getName().equals(qElement)){
			        	   value = element.getAttributeByName(qAttribute).getValue();
			        	   break;
			           }//if
			      }//if
			 }//while
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}//try
			}//if
		}//try
		 return value;
	}//getAttributeValue
	
	public static String getSex(String xmlFile){
		return getAttributeValue(xmlFile,"书","名称");
	}
	
	public static String getXmlVersion(String xmlFile){
		 String version = null;
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 XMLEventReader  reader = null;
		 try {
			reader = inputFactory.createXMLEventReader(new FileReader(xmlFile));
			 while (reader.hasNext()){
			      XMLEvent event = reader.nextEvent();
			      if (event.isStartDocument()){
			           StartDocument startDocument = (StartDocument)event;
			           version = startDocument.getVersion();
			           break;
			      }//if
			 }//while
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}//try
			}//if
		}//try
		return version;
	}
	
	public static String getXmlEncoding(String xmlFile){
		String encoding = null;
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 XMLEventReader  reader = null;
		 try {
			reader = inputFactory.createXMLEventReader(new FileReader(xmlFile));
			 while (reader.hasNext()){
			      XMLEvent event = reader.nextEvent();
			      if (event.isStartDocument()){
			           StartDocument startDocument = (StartDocument)event;
			           encoding = startDocument.getCharacterEncodingScheme();
			           break;
			      }//if
			 }//while
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}//try
			}//if
		}//try
		return encoding;
	}
	
	public static boolean isStandalone(String xmlFile){
		boolean alone = false;
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 XMLEventReader  reader = null;
		 try {
			reader = inputFactory.createXMLEventReader(new FileReader(xmlFile));
			 while (reader.hasNext()){
			      XMLEvent event = reader.nextEvent();
			      if (event.isStartDocument()){
			           StartDocument startDocument = (StartDocument)event;
			           if(!startDocument.standaloneSet()){
			        	   alone = true;
			           }else{
			        	   alone = startDocument.isStandalone();
			           }
			           break;
			      }//if
			 }//while
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}//try
			}//if
		}//try
		return alone;
	}
}
