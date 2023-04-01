package com.thinkbox.test.convert;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvToJson {
    public static void main(String[] args) {
        String csvData = "1\tJohn\tDoe\n2\tJane\tDoe\n3\tBob\tSmith";
        try {
            CsvSchema csvSchema = buildCsvSchema(csvData, true, '\t', "\n", "COL");
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Object> mappingIterator = csvMapper.reader().forType(Object.class).with(csvSchema).readValues(csvData);
            List<Object> list = null;
            list = mappingIterator.readAll();
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("OUTPUT:" + mapper.writeValueAsString(list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CsvSchema buildCsvSchema(String csvData, boolean hasHeader, char columnSeparator, String lineSeparator, String columnPrefix) {
        CsvSchema.Builder builder = CsvSchema.builder();
        if (!hasHeader) {
            String firstLine = csvData.substring(0, csvData.indexOf(lineSeparator));
            String[] elements = firstLine.split(String.valueOf(columnSeparator));
            for (int i = 1; i <= elements.length; i++) {
                builder = builder.addColumn(columnPrefix + i);
            }
        }
        return builder.setUseHeader(hasHeader).setColumnSeparator(columnSeparator).setLineSeparator(lineSeparator).build();
    }
}