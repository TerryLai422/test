package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class JsonToXml {
    public static void main(String[] args) {
        String json = "{\"example\":{\"name\":\"John\",\"age\":30}}";
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            Object jsonObject = jsonMapper.readValue(json, Object.class);
            System.out.println("jsonObject:" + jsonObject);

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(xmlMapper.writeValueAsString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
