package org.library.model;

public class Student {

    private String usn;
    private String name;

    // All-args constructor
    public Student(String usn, String name) {
        setUsn(usn);
        setName(name);
    }

    public Student() {

    }

    // Getters and Setters

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ USN cannot be empty.");
        }

        // Optional: Normalize & validate format (e.g., alphanumeric)
        String normalized = usn.trim().toUpperCase();
        if (!normalized.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("❌ USN must contain only letters and digits.");
        }

        this.usn = normalized;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Student name cannot be empty.");
        }

        this.name = name.trim();
    }

    @Override
    public String toString() {
        return "Student[USN=" + usn + ", Name=" + name + "]";
    }
}
