package com.thinkbox.test.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.util.List;
import java.util.Map;

public class JsonToCsv {
    public static void main(String[] args) {
        String content = "[{\"num\":\"2\",\"firstName\":\"Mary\",\"nickName\":\"Doe\"},{\"num\":\"3\",\"firstName\":\"John\",\"nickName\":\"Smith\"}]";
        JsonToCsv jsonToCsv = new JsonToCsv();
        System.out.println(jsonToCsv.convert(content));
    }
    public String convert(String content) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            List<Map<?,?>> object = jsonMapper.readValue(content, List.class);
            CsvMapper mapper = new CsvMapper();
            return mapper.writer(buildCsvSchema(object, true, ',', "\n")).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public CsvSchema buildCsvSchema(List<Map<?,?>> object, boolean hasHeader, char columnSeparator, String lineSeparator) {
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        Map<?, ?> first = object.get(0);
        for (Object key : first.keySet()) {
            schemaBuilder.addColumn(key.toString(), CsvSchema.ColumnType.STRING);
        }
        return schemaBuilder.setUseHeader(hasHeader).setColumnSeparator(columnSeparator).setLineSeparator(lineSeparator).build();
    }
}