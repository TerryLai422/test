package com.thinkbox.test.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonToCsv {
    public static void main(String[] args) throws IOException {
        // Create CsvMapper object and configure settings
        String json = "[{\"num\":\"2\",\"firstName\":\"Mary\",\"nickName\":\"Doe\"},{\"num\":\"3\",\"firstName\":\"John\",\"nickName\":\"Smith\"}]";
        ObjectMapper jsonMapper = new ObjectMapper();
        List<Map> object = jsonMapper.readValue(json, List.class);

        CsvMapper mapper = new CsvMapper();
        System.out.println(mapper.writer(buildCsvSchema( object, true, ',', "\n")).writeValueAsString(object));
    }

    public static CsvSchema buildCsvSchema(List<Map> object, boolean hasHeader, char columnSeparator, String lineSeparator) {
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        Map<?, ?> first = object.get(0);
        for (Object key : first.keySet()) {
            schemaBuilder.addColumn(key.toString(), CsvSchema.ColumnType.STRING);
        }
        return schemaBuilder.setUseHeader(hasHeader).setColumnSeparator(columnSeparator).setLineSeparator(lineSeparator).build();
    }
}