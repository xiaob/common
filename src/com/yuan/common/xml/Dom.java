package com.yuan.common.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class Dom {
	
	private Element e;
	
	private Dom(Element element){
		this.e = element;
	}
	
	public void print(){
		NodeList nodeList = e.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			System.out.println("节点名: " + node.getNodeName() + ", 节点值: " + node.getNodeValue() + ", 节点类型: " + node.getNodeType());
		}
	}
	
	public Element getDomElement(){
		return e;
	}
	
	public Document getDocument(){
		return e.getOwnerDocument();
	}
	
	public static Dom newDom(String rootName)throws XmlException{
		Document doc = null;
		try {
			DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dombuilder.newDocument();
			doc.setXmlStandalone(true);
		} catch (Exception e) {
			throw new XmlException(e.getMessage(), e);
		} 
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		return new Dom(root);
	}
	
	public static Dom getRoot(InputStream is)throws XmlException{
		Document doc = null;
		try {
			DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dombuilder.parse(is);
		} catch (Exception e) {
			throw new XmlException(e.getMessage(), e);
		} 
		Element root = doc.getDocumentElement();
		return new Dom(root);
	}
	
	public static Dom getRoot(String xmlFile)throws XmlException{
		InputStream is = null;
		try {
			is = new FileInputStream(xmlFile);
			Dom root = getRoot(is);
			return root;
		} catch (Exception e) {
			throw new XmlException(e.getMessage(), e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					throw new XmlException(e.getMessage(), e);
				}
			}
		}
	}
	
	public static Dom getRoot(File xmlFile)throws XmlException{
		return getRoot(xmlFile.getAbsolutePath());
	}
	
	public static Dom parse(String xml) throws XmlException{
		try {
			return getRoot(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}
	
	public static Dom parse(byte[] xmlData) throws XmlException{
		return getRoot(new ByteArrayInputStream(xmlData));
	}
	
	public String getAttributeValue(String attributeName){
		return e.getAttribute(attributeName);
	}
	
	public boolean existElement(String elementName){
		NodeList nodeList = e.getElementsByTagName(elementName);
		if((nodeList == null) || (nodeList.getLength() < 1)){
			return false;
		}
		return true;
	}
	
	public String elementText(String elementName){
		Element element = (Element)e.getElementsByTagName(elementName).item(0);
		Node textNode =  element.getFirstChild();
		if(textNode == null){
			return "";
		}
		return textNode.getNodeValue();
	}
	
	public Dom element(String elementName){
		NodeList nodeList = e.getElementsByTagName(elementName);
		if((nodeList == null) || (nodeList.getLength() < 1)){
			return null;
		}
		Element element = (Element)nodeList.item(0);
		return new Dom(element);
	}
	
	public List<Dom> elements(String elementName){
		List<Dom> eList = new ArrayList<Dom>();
		NodeList nodeList = e.getElementsByTagName(elementName);
		if(nodeList == null){
			return eList;
		}
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element)node;
				eList.add(new Dom(element));
			}
		}
		return eList;
	}
	public List<Dom> elements(){
		List<Dom> eList = new ArrayList<Dom>();
		NodeList nodeList = e.getChildNodes();
		if(nodeList == null){
			return eList;
		}
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element)node;
				eList.add(new Dom(element));
			}
		}
		return eList;
	}
	
	public String getElementName(){
		return e.getTagName();
	}
	
	public Dom addElement(String name){
		Document document = e.getOwnerDocument();
		Element element = document.createElement(name);
		e.appendChild(element);
		return new Dom(element);
	}
	
	public Dom addElement(String name, String value){
		Document document = e.getOwnerDocument();
		Element element = document.createElement(name);
		e.appendChild(element);
		Text text = document.createTextNode(value);
		element.appendChild(text);
		return new Dom(element);
	}
	
	//添加或修改属性
	public Dom setAttribute(String name, String value){
		e.setAttribute(name, value);
		return this;
	}
	
	public void remove(Dom subDom){
		e.removeChild(subDom.getDomElement());
	}
	
	public void removeElement(String name){
		NodeList nodeList = e.getElementsByTagName(name);
		if(nodeList == null){
			return ;
		}
		for(int i=0;i<nodeList.getLength();i++){
			e.removeChild(nodeList.item(i));
		}
	}
	
	public void removeAttribute(String name){
		e.removeAttribute(name);
	}
	
	public Dom updateElementText(String name, String value){
		Element element = (Element)e.getElementsByTagName(name).item(0);
		Node textNode =  element.getFirstChild();
		textNode.setNodeValue(value);
		return new Dom(element);
	}
	
	public Dom updateElementText(String value){
		Node textNode =  e.getFirstChild();
		textNode.setNodeValue(value);
		return this;
	}
	
	public String getElementText(){
		Node textNode =  e.getFirstChild();
		return textNode.getNodeValue();
	}
	
	public boolean hasChildren(){
		return e.hasChildNodes();
	}
	
	public void write(OutputStream os){
		write(os, "UTF-8");
	}
	public void write(OutputStream os, String encoding){
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
//			tFactory.setAttribute("indent-number", 2);
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes"); 
			transformer.transform(new DOMSource(e.getOwnerDocument()), new StreamResult(new OutputStreamWriter(os)));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	public void write(String xmlFile)throws XmlException{
		write(xmlFile, "UTF-8");
	}
	public void write(String xmlFile, String encoding)throws XmlException{
		try {
			OutputStream os = new FileOutputStream(xmlFile);
			write(os, encoding);
			os.close();
		} catch (Exception e) {
			throw new XmlException(e.getMessage(), e);
		}
	}
	public String toString(String encoding)throws XmlException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		write(baos, encoding);
		try {
			return baos.toString(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}
	public String toString(){
		
		return toString("UTF-8");
	}

	//去除文档中的空行
	protected Document trim(){
		Document document = e.getOwnerDocument();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.TEXT_NODE){
				String text = node.getNodeValue();
				node.setNodeValue(text.trim());
			}
		}
		return document;
	}
	
	public static void main(String args[])throws Exception{
		InputStream is = new FileInputStream("d:/test/t.xml");
		Dom root = Dom.getRoot(is);
//		root.print();
		is.close();
		List<Dom> pList = root.elements("property");
		Dom d = pList.get(0);
		Dom nameDom = d.element("name");
		nameDom.setAttribute("a", "2");
		nameDom.setAttribute("b", "3");
		nameDom.updateElementText("测试呵呵");
		d.updateElementText("description", "按当地");
		root.remove(pList.get(pList.size() - 1));
		OutputStream os = new FileOutputStream("d:/test/t.xml");
		root.write(os);
//		List<Dom> propertyElementList = root.elements("property");
//		Dom d = propertyElementList.get(0);
//		Dom nameDom = d.element("name");
//		nameDom.print();
//		for(Dom d : propertyElementList){
//			System.out.println(d.elementText("name") + ", " + d.elementText("value") + ", " + d.elementText("description"));
//		}
		os.close();
		
	}

}
