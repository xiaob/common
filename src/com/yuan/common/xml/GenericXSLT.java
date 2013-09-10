package com.yuan.common.xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GenericXSLT {
	
	public static void transform(Document document ) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			SAXResult result = new SAXResult();
			result.setHandler(new ContentHandler());
			transformer.transform(new DOMSource(document), result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void transform(InputStream is, OutputStream os){
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			XMLOutputFactory f = XMLOutputFactory.newInstance();
			XMLEventWriter w = f.createXMLEventWriter(os);
			StAXResult result = new StAXResult(w);
			transformer.transform(new StreamSource(is), result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])throws Exception{
		transform(new FileInputStream("e:/build.xml"),System.out);
	}

}

class ContentHandler extends DefaultHandler {

    @Override
    public void characters(char[] ch, int start, int length) 
		throws SAXException {
		String name = new String(ch, start, length);
        System.out.print(name + "\t");
    }
}
