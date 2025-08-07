package org.library.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IssueTest {
    @Test
    void testIssueCreationWithValidData () {
        Student student  = new Student("S001", "Alice");
        Book book = new Book("978-3-16-148410-0", "Test Book", "Fiction", 1);
        Issue issue = new Issue("2025-08-07", "2025-08-14", student, book);

        assertEquals("2025-08-07", issue.getIssueDate());
        assertEquals("2025-08-14", issue.getReturnDate());
        assertEquals(student, issue.getIssueStudent());
        assertEquals(book, issue.getIssueBook());
    }

    @Test
    void testSettersAndGetters() {
        Issue issue = new Issue();
        issue.setIssueId(123);
        issue.setIssueDate("2025-08-07");
        issue.setReturnDate("2025-08-14");

        Student student = new Student("S002", "Isa");
        Book book = new Book("978-3-16-148411-0", "Fervor", "Mystery", 2);

        issue.setIssueStudent(student);
        issue.setIssueBook(book);

        assertEquals(123, issue.getIssueId());
        assertEquals("2025-08-07", issue.getIssueDate());
        assertEquals("2025-08-14", issue.getReturnDate());
        assertEquals(student, issue.getIssueStudent());
        assertEquals(book, issue.getIssueBook());
    }
}
