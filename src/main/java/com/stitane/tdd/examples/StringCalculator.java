package com.stitane.tdd.examples;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ";";
    private static final String DELIMITER = "[,n]";
    private static final String MORE_THAN_TWO_NUMBERS = "More than two numbers";
    private static final String DELIMITER_START_WITH = "//";
    private static final String DELIMITER_NEW_LINE = "n";
    private static final String BRACKET_DELIMITER_OPENED = "[";
    private static final String BRACKET_DELIMITER_CLOSED = "]";

    public static int add(String numbers) {

        if (numbers.startsWith(DELIMITER_START_WITH)) {
            int beginDelimiterIndex = numbers.indexOf(DELIMITER_START_WITH) + 2;
            int endDelimiterIndex = numbers.indexOf(DELIMITER_NEW_LINE);
            String delimiter = numbers.substring(beginDelimiterIndex, endDelimiterIndex);
            System.out.println("Delimiter : " + delimiter);
            if (delimiter.contains(BRACKET_DELIMITER_OPENED)) {
                List<String> delimiters = extractDelimiters(delimiter);
                return add(delimiters, numbers.substring(endDelimiterIndex +1));
            }
            return add(delimiter, numbers.substring(endDelimiterIndex +1));
        }

        return sum(numbers.split(DELIMITER));
    }

    private static List<String> extractDelimiters(String delimiter) {
        List<String> delimiters = new ArrayList<>();
        int beginIndex = delimiter.indexOf(BRACKET_DELIMITER_OPENED);
        int endIndex = delimiter.indexOf(BRACKET_DELIMITER_CLOSED);
        while (delimiter.contains(BRACKET_DELIMITER_OPENED)) {
            delimiters.add(delimiter.substring(beginIndex + 1, endIndex));
            delimiter = delimiter.substring(endIndex + 1);
        }
        return delimiters;
    }

    private static int add(String delimiter, String numbers) {
        return sum(numbers.split(delimiter));
    }

    private static int add(List<String> delimiters, String numbers) {
        for (String delimiter : delimiters) {
            numbers =  numbers.replaceAll(delimiter, DEFAULT_DELIMITER);
        }
        return sum(numbers.split(DEFAULT_DELIMITER));
    }

    private static int sum(String[] numbers) {
        int result = 0;
        List<Integer> negativesNumbers = new ArrayList<>();
        for (String number: numbers) {
            if (!number.trim().isEmpty()) {
                int n = Integer.parseInt(number);
                if (n < 0) {
                    negativesNumbers.add(n);
                }
                if (n <= 1000) {
                    result += n;
                }
            }
        }
        if(!negativesNumbers.isEmpty()) {
            throw new RuntimeException("Negatives not allowed: " + negativesNumbers);
        }
        return result;
    }

    // disabled
    private static void isGreaterThen2Numbers(String[] strings) {
        if (strings.length > 2) {
            throw new RuntimeException(MORE_THAN_TWO_NUMBERS);
        }
    }
}
