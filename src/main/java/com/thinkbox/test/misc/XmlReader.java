package com.thinkbox.test.misc;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;
import com.jayway.jsonpath.spi.mapper.JsonOrgMappingProvider;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlReader {
    public static void main(String[] args) throws Exception {
        String xmlString = "<root><person><name>John</name><age>30</age></person></root>";

        // Convert XML to JSON
        JSONObject json = xmlToJson(xmlString);

        // Query the JSON using JsonPath
        Configuration conf = Configuration.builder()
                .jsonProvider(new JsonOrgJsonProvider())
                .mappingProvider(new JsonOrgMappingProvider())
                .build();
        DocumentContext documentContext = JsonPath.using(conf).parse(json.toString());

        String jsonPath = "$.root.person.name";
        String name = documentContext.read(jsonPath);

        System.out.println("Name: " + name);
    }

    private static JSONObject xmlToJson(String xmlString) throws Exception {
        InputSource inputSource = new InputSource(new StringReader(xmlString));
        org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);

        Configuration conf = Configuration.builder()
                .jsonProvider(new JsonOrgJsonProvider())
                .mappingProvider(new JsonOrgMappingProvider())
                .build();
        String json = conf.jsonProvider().toJson(doc);
        System.out.println("Json:" + json);
        return new JSONObject(json);
    }
}
