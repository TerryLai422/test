package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlToJson {
    public static void main(String[] args) {
        String content = "<example><name>John</name><age>30</age></example>";
        XmlToJson xmlToJson = new XmlToJson();
        System.out.println(xmlToJson.convert(content));
    }
    public String convert(String content) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Object json = xmlMapper.readValue(content, Object.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return jsonMapper.writeValueAsString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}