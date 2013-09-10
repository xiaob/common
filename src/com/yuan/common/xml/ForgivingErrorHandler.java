package com.yuan.common.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ForgivingErrorHandler implements ErrorHandler {

	public void error(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage());
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage());
	}

	public void warning(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage());
	}

}
