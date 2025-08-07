package org.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.library.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {

    @TempDir
    File tempDir;

    private File testFile;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File(tempDir, "test_students.csv");
        studentRepository = new StudentRepository(testFile.getAbsolutePath());
    }

    @Test
    void testSaveAndFindAll() {
        studentRepository.save(new Student("S001", "Test User"));

        List<Student> students = studentRepository.findAll();
        assertEquals(1, students.size());
        assertEquals("Test User", students.get(0).getName());
    }
}
