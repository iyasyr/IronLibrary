package org.library.util;

public class IsbnUtil {

    // Normalize ISBN by removing dashes and trimming spaces
    public static String normalize(String isbn) {
        return isbn == null ? "" : isbn.replaceAll("-", "").trim();
    }

    // Validate ISBN format: 10 or 13 digits after normalization
    public static void validate(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }

        String clean = normalize(isbn);
        if (!clean.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be 10 or 13 digits (dashes optional).");
        }
    }
}
