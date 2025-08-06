package org.example.model;

public class Student {
    private String usn;
    private String name;


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

        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("The USN cannot be empty.");
        }
        this.usn = usn;
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        this.name = name;
    }


    @Override
    public String toString() {
        return "Student[USN=" + usn + ", Name=" + name + "]";
    }
}