package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlToJson {
    public static void main(String[] args) {
        String xml = "<example><name>John</name><age>30</age></example>";
        try {
            // Create an XmlMapper to read XML data
            XmlMapper xmlMapper = new XmlMapper();
            Object json = xmlMapper.readValue(xml, Object.class);
            // Create an ObjectMapper to write JSON data
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(jsonMapper.writeValueAsString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
