package com.qa.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;

public class Utilities {
    public static final long WAIT = 10;
    public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<>();

        // Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Build Document
        Document document = builder.parse(file);

        // Normalize the XML Structure
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        // Get all Elements
        NodeList nodeList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nodeList.getLength(); temp++){
            Node node = nodeList.item((temp));
            if ((node.getNodeType() == Node.ELEMENT_NODE)){
                Element element = (Element) node;
                // Store each element Key value in Map
                stringMap.put(element.getAttribute("name"), element.getTextContent());
            }
        }
        return stringMap;
    }
}