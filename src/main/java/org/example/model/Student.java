package org.example.model;

public class Student {
    private String usn;
    private String name;

    /* Parameterized constructor using the setters to make sure that the validations are applied and to avoid duplicate code. */
    public Student(String usn, String name) {
        setUsn(usn);
        setName(name);
    }

    // Getters
    public String getUsn() {
        return usn;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setUsn(String usn) {
        // Validation to avoid creating students with an empty USN:
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("The USN cannot be empty.");
        }
        this.usn = usn;
    }

    public void setName(String name) {
        // Validation to avoid creating students with an empty name:
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        this.name = name;
    }

    /* A method for printing student information clearly in logs and console,
    especially when listing or debugging students by USN.
     */
    @Override
    public String toString() {
        return "Student[USN=" + usn + ", Name=" + name + "]";
    }
}