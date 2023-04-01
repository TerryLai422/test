package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class JsonToXml {
    public static void main(String[] args) {
        String content = "{\"example\":{\"name\":\"John\",\"age\":30}}";
        JsonToXml jsonToXml = new JsonToXml();
        System.out.println(jsonToXml.convert(content));
    }
    public String convert(String content) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            Object jsonObject = jsonMapper.readValue(content, Object.class);
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return xmlMapper.writeValueAsString(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}