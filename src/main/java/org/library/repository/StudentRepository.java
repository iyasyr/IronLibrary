package org.library.repository;

import org.library.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentRepository {

    private static final String DEFAULT_FILE_PATH = "students.csv";
    private final String filePath;

    // Default constructor uses the production file
    public StudentRepository() {
        this.filePath = DEFAULT_FILE_PATH;
    }

    // This one is for tests or custom file injection
    public StudentRepository(String filePath) {
        this.filePath = filePath;
    }

    // Guardar un estudiante (append mode)
    public void save(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(toCsvLine(student));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    // Obtener todos los estudiantes
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(fromCsvLine(line));
            }
        } catch (FileNotFoundException e) {
            // archivo aún no existe
        } catch (IOException e) {
            throw new RuntimeException("Error reading students", e);
        }
        return students;
    }

    // Buscar por USN
    public Student findByUsn(String usn) {
        return findAll().stream()
                .filter(s -> s.getUsn().equalsIgnoreCase(usn))
                .findFirst()
                .orElse(null);
    }

    // Convertir estudiante a línea CSV
    private String toCsvLine(Student student) {
        return String.join(",",
                escape(student.getUsn()),
                escape(student.getName())
        );
    }

    // Convertir línea CSV a objeto Student
    private Student fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        return new Student(
                unescape(parts[0]),
                unescape(parts[1])
        );
    }

    public Map<String, Student> toMap() {
        return findAll().stream()
                .collect(Collectors.toMap(
                        Student::getUsn,
                        s -> s,
                        (existing, replacement) -> existing // handle duplicates if any
                ));
    }

    // Escapar comas
    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    // Des-escapar comas
    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}

