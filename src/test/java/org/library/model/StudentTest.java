package org.library.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreationWithValidData() {
        Student student = new Student("S001", "Alice");

        assertEquals("S001", student.getUsn());
        assertEquals("Alice", student.getName());
    }

    @Test
    void testSetInvalidUsn() {
        Student student = new Student();
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> student.setUsn(null));
        assertEquals("The USN cannot be empty.", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> student.setUsn("   "));
        assertEquals("The USN cannot be empty.", ex2.getMessage());
    }

    @Test
    void testSetInvalidName() {
        Student student = new Student();
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> student.setName(null));
        assertEquals("The name cannot be empty.", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> student.setName("  "));
        assertEquals("The name cannot be empty.", ex2.getMessage());
    }
}
