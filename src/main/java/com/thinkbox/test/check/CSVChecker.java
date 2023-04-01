package com.thinkbox.test.check;

public class CSVChecker {
    public static boolean isCSV(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        boolean inQuotes = false;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '\"') {
                inQuotes = !inQuotes;
            } else if (ch == ',') {
                if (!inQuotes) {
                    count++;
                }
            }
        }
        return count > 0 || inQuotes;
    }

    public static void main(String[] args) {
        String csvString = "John,Doe,\"john.doe@example.com\",35";
        System.out.println(isCSV(csvString));
    }
}
