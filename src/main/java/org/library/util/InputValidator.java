package org.library.util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    public static void validateNonEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    public static void validateQuantity(String input) {
        try {
            int q = Integer.parseInt(input);
            if (q < 0) {
                throw new IllegalArgumentException("Quantity must be 0 or greater.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid number.");
        }
    }

    public static void validateEmail(String email) {
        validateNonEmpty(email, "Email");
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }
}
