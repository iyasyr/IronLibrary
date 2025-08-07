package org.library.repository;

import org.library.model.Student;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository {

    private static final String DEFAULT_FILE_PATH = "students.csv";
    private final String filePath;

    public StudentRepository() {
        this.filePath = DEFAULT_FILE_PATH;
    }

    public StudentRepository(String filePath) {
        this.filePath = filePath;
    }

    // Save a student (append mode)
    public void save(Student student) {
        validateStudent(student);
        checkUsnUniqueness(student.getUsn());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(toCsvLine(student));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    // Get all students
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    students.add(fromCsvLine(line));
                } catch (Exception e) {
                    System.err.println("⚠️ Skipping malformed student line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // No file yet — treat as empty
        } catch (IOException e) {
            throw new RuntimeException("Error reading students", e);
        }
        return students;
    }

    // Find by USN
    public Student findByUsn(String usn) {
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ USN cannot be null or empty.");
        }
        return findAll().stream()
                .filter(s -> s.getUsn().equalsIgnoreCase(usn.trim()))
                .findFirst()
                .orElse(null);
    }

    // Map representation: USN → Student
    public Map<String, Student> toMap() {
        return findAll().stream()
                .collect(Collectors.toMap(
                        Student::getUsn,
                        s -> s,
                        (existing, replacement) -> existing // In case of duplicate USNs
                ));
    }

    // Validation logic
    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("❌ Student cannot be null.");
        }
        student.setUsn(student.getUsn());   // Re-apply internal validation
        student.setName(student.getName());
    }

    private void checkUsnUniqueness(String usn) {
        String normalized = usn.trim().toUpperCase();
        boolean exists = findAll().stream()
                .anyMatch(s -> s.getUsn().trim().equalsIgnoreCase(normalized));
        if (exists) {
            throw new IllegalArgumentException("❌ Student with USN already exists: " + normalized);
        }
    }

    // Convert student → CSV
    private String toCsvLine(Student student) {
        return String.join(",",
                escape(student.getUsn()),
                escape(student.getName())
        );
    }

    // Parse line → Student
    private Student fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid CSV line for student");
        }
        return new Student(
                unescape(parts[0]),
                unescape(parts[1])
        );
    }

    // Escape comma
    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    // Unescape comma
    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
