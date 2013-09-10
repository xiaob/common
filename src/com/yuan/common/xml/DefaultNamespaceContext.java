package com.yuan.common.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class DefaultNamespaceContext implements NamespaceContext {
	
	private Map<String, String> namespaceMap = new HashMap<String, String>();
	
	public DefaultNamespaceContext(Map<String, String> namespaceMap){
		this.namespaceMap = namespaceMap;
	}

	public String getNamespaceURI(String prefix) {
		System.out.println("prefix = " + prefix);
		if (prefix == null)
			throw new NullPointerException("Null prefix");
		else if ("xml".equals(prefix))
			return XMLConstants.XML_NS_URI;
		else if(namespaceMap.containsKey(prefix)){
			return namespaceMap.get(prefix);
		}
		return XMLConstants.NULL_NS_URI;
	}

	public String getPrefix(String namespaceURI) {
		System.out.println("namespaceURI = " + namespaceURI);
		throw new UnsupportedOperationException();
	}

	public Iterator<?> getPrefixes(String namespaceURI) {
		System.out.println("namespaceURI = " + namespaceURI);
		throw new UnsupportedOperationException();
	}

}
