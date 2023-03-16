package com.thinkbox.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlToJsonConverter {

    public static void main(String[] args) {
        String xml = "<example><name>John</name><age>30</age></example>";

        try {
            // Create an XmlMapper to read XML data
            XmlMapper xmlMapper = new XmlMapper();
            Object json = xmlMapper.readValue(xml, Object.class);

            // Create an ObjectMapper to write JSON data
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonString = jsonMapper.writeValueAsString(json);

            // Output the JSON string to console
            System.out.println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
