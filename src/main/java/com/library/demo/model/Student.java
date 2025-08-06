package com.library.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private String usn;

    private String name;

    public Student(String usn, String name) {
        setUsn(usn);
        setName(name);
    }

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
