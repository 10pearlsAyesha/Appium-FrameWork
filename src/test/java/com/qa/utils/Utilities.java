package com.qa.utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.ServerSocket;
public class Utilities {
    public static final long WAIT = 10;
    public static final long WAITFORCLICK = 40;
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
        log().info(root.getNodeName());
        // Get all Elements
        NodeList nodeList = document.getElementsByTagName("string");
        log().info("==========");
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
    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public boolean checkIfAppiumServerIsRunning(int port) {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException exception) {
            log().info("True");
            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }
}