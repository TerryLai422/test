package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonToXml {

    public static void main(String[] args) {
        String json = "{\"example\":{\"name\":\"John\",\"age\":30}}";

        try {
            // Create an ObjectMapper to read JSON data
            ObjectMapper jsonMapper = new ObjectMapper();
            Map<String, Object> jsonObject = jsonMapper.readValue(json, HashMap.class);
            System.out.println("jsonObject:" + jsonObject);
            // Get the first key/value pair from the JSON object
            Map.Entry<String, Object> entry = jsonObject.entrySet().iterator().next();
            String rootElementName = entry.getKey();
            System.out.println("rootElementName:" + rootElementName);
            Object rootElementValue = entry.getValue();

            // Create a new map with the first key/value pair as the only entry
            Map<String, Object> rootMap = new HashMap<>();
            rootMap.put(rootElementName, rootElementValue);

            // Create an XmlMapper to write XML data
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String xmlString = xmlMapper.writeValueAsString(rootMap);

            // Output the XML string to console
            System.out.println(xmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
