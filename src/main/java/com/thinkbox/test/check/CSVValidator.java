package com.thinkbox.test.check;

public class CSVValidator {
    public static boolean isValidCSV(String csv) {
        String[] lines = csv.split("\n");
        if (lines.length == 0) {
            return false; // Empty CSV
        }
        String delimiter = null;
        for (String line : lines) {
            String[] values = line.split(",");
            if (values.length == 0) {
                return false; // Empty row
            }
            if (delimiter == null) {
                delimiter = detectDelimiter(line);
                if (delimiter == null) {
                    return false; // Unable to detect delimiter
                }
            }
            for (String value : values) {
                if (!isValidCSVValue(value)) {
                    return false; // Invalid CSV value
                }
            }
        }
        return true;
    }

    private static boolean isValidCSVValue(String value) {
        // TODO: Implement validation logic for CSV values
        return true;
    }

    private static String detectDelimiter(String line) {
        // TODO: Implement delimiter detection logic for CSV
        return ",";
    }
    
    public static void main(String[] args) {
        String csv1 = "Name,Age,Gender\nJohn,25,Male\nJane,30,Female";
        String csv2 = "Name;Age;Gender\nJohn;25;Male\nJane;30;Female";
        String csv3 = "Name\tAge\tGender\nJohn\t25\tMale\nJane\t30\tFemale";
        String csv4 = "Name,Age,Gender\nJohn,25,Male\nJane,30,Female,Extra Field";

        System.out.println("CSV 1 is valid: " + CSVValidator.isValidCSV(csv1));
        System.out.println("CSV 2 is valid: " + CSVValidator.isValidCSV(csv2));
        System.out.println("CSV 3 is valid: " + CSVValidator.isValidCSV(csv3));
        System.out.println("CSV 4 is valid: " + CSVValidator.isValidCSV(csv4));
    }
}
