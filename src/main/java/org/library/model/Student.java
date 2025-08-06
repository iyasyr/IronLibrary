package org.library.model;

public class Student {

    private String usn;
    private String name;

    // Constructor vacío
    public Student() {
    }

    // Constructor con todos los campos
    public Student(String usn, String name) {
        setUsn(usn);
        setName(name);
    }

    // Getters y Setters

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("The USN cannot be empty.");
        }
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        this.name = name;
    }

    // toString

    @Override
    public String toString() {
        return "Student[USN=" + usn + ", Name=" + name + "]";
    }
}
