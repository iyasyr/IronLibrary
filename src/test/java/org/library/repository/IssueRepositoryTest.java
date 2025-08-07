package org.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.library.model.Book;
import org.library.model.Issue;
import org.library.model.Student;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssueRepositoryTest {

    @TempDir
    File tempDir;

    private File testFile;
    private IssueRepository issueRepository;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File(tempDir, "test_issue.csv");
        issueRepository = new IssueRepository(testFile.getAbsolutePath());
    }

    @Test
    void testSaveAndFindAll() {
        Student student = new Student("S001", "Isa");
        Book book = new Book("978-3-16-148410-0", "Text Book", "Fiction", 1);
        Issue issue = new Issue("2025-08-07", "2025-08-14", student, book);

        issueRepository.save(issue);

        Map<String, Student> studentMap = new HashMap<>();
        studentMap.put(student.getUsn(), student);

        Map<String, Book> bookMap = new HashMap<>();
        bookMap.put(book.getIsbn(), book);

        List<Issue> issues = issueRepository.findAll(studentMap, bookMap);

        assertEquals(1, issues.size());
        assertEquals("2025-08-07", issues.get(0).getIssueDate());
        assertEquals("2025-08-14", issues.get(0).getReturnDate());
        assertEquals("Isa", issues.get(0).getIssueStudent().getName());
        assertEquals("Text Book", issues.get(0).getIssueBook().getTitle());

    }
}
