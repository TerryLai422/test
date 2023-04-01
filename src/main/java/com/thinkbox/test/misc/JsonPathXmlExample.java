package com.thinkbox.test.misc;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonPathXmlExample {

    public static void main(String[] args) {
        String xml = "<root><person><name>John</name><age>30</age></person></root>";
        JSONObject jsonObject = convertXmlToJson(xml);
        System.out.println(jsonObject != null?jsonObject.toString():null);
    }

    public static JSONObject convertXmlToJson(String xml) {
        try {
            org.json.JSONObject jsonObject = XML.toJSONObject(xml);
            String json = jsonObject.toString();
            System.out.println("JSON: " + json);
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
