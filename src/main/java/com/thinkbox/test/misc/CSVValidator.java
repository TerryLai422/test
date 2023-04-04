package com.thinkbox.test.misc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVValidator {

    private static final char DELIMITER = ',';
    private static final char QUOTE_CHAR = '"';

    public static void main(String[] args) {
        String filename = "data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isValidCSVLine(line)) {
                    System.out.println("Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidCSVLine(String line) {
        // Check for empty line
        if (line.trim().isEmpty()) {
            return false;
        }
        // Split line into fields
        String[] fields = line.split(Character.toString(DELIMITER), -1);
        // Check for uneven number of quote characters
        int numQuotes = 0;
        for (String field : fields) {
            numQuotes += countOccurrences(field, QUOTE_CHAR);
        }
        if (numQuotes % 2 != 0) {
            return false;
        }
        // Check for unescaped delimiter within a field
        for (String field : fields) {
            if (field.startsWith(Character.toString(QUOTE_CHAR)) &&
                    field.endsWith(Character.toString(QUOTE_CHAR))) {
                String innerField = field.substring(1, field.length() - 1);
                if (innerField.contains(Character.toString(DELIMITER)) &&
                        !innerField.contains(Character.toString(QUOTE_CHAR))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int countOccurrences(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
}