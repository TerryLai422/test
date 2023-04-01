package com.thinkbox.test.check;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.StringReader;
import java.io.IOException;

public class XMLValidator {
    public static boolean isValidXML(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new InputSource(new StringReader(xml)));
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return false;
        }
    }
    
	public static void main(String[] args) {
		String xml = "<root><child>Hello, world!</child></root>";
		if (XMLValidator.isValidXML(xml)) {
		    System.out.println("Valid XML!");
		} else {
		    System.out.println("Not valid XML!");
		}
	}
}