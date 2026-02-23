package com.readdit.util;

public class RegexHelper {

    public static String toSlug(String input) {
        return input
                .toLowerCase() // lowercase
                .replaceAll("[^a-z0-9\\s-]", "") // remove special characters
                .trim() // remove leading/trailing spaces
                .replaceAll("\\s+", "-"); // replace spaces with dash
    }

    public static int extractDigits(String input) {
        if (input == null) return 0;
        String processed = input.replaceAll("\\D", "");
        return processed.isEmpty() ? 0 : Integer.parseInt(processed);
    }
}