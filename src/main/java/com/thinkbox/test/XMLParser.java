package com.thinkbox.test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class XMLParser {
    public static String getValueFromXML(String xml, String xpathExpression) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xpath.compile(xpathExpression).evaluate(doc, XPathConstants.NODESET);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }
    
	public static void main(String[] args) {
		String xml = "<root><child>Hello, world!</child></root>";
		String xpathExpression = "/root/child";
		try {
		    String value = XMLParser.getValueFromXML(xml, xpathExpression);
		    System.out.println("Value: " + value);
		} catch (Exception e) {
		    System.out.println("Error: " + e.getMessage());
		}
	}
}