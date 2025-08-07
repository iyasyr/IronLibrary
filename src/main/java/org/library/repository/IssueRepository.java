package org.library.repository;

import org.library.model.Book;
import org.library.model.Issue;
import org.library.model.Student;

import java.io.*;
import java.util.*;

public class IssueRepository {

    private static final String DEFAULT_FILE_PATH = "issues.csv";
    private final String filePath;
    private static int nextId = 1;

    // Default constructor (used in production)
    public IssueRepository() {
        this.filePath = DEFAULT_FILE_PATH;
    }

    // Constructor for tests (or custom paths)
    public IssueRepository(String filePath) {
        this.filePath = filePath;
    }

    // Save a new Issue
    public void save(Issue issue) {
        validateIssue(issue);
        issue.setIssueId(nextId++);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.format("%d,%s,%s,%s,%s",
                    issue.getIssueId(),
                    escape(issue.getIssueDate()),
                    escape(issue.getReturnDate()),
                    escape(issue.getIssueStudent().getUsn()),
                    escape(issue.getIssueBook().getIsbn())
            ));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving issue", e);
        }
    }

    // Load all Issues
    public List<Issue> findAll(Map<String, Student> studentMap, Map<String, Book> bookMap) {
        List<Issue> issues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String issueDate = unescape(parts[1]);
                        String returnDate = unescape(parts[2]);
                        String usn = unescape(parts[3]);
                        String isbn = unescape(parts[4]);

                        Student student = studentMap.get(usn);
                        Book book = bookMap.get(isbn);

                        if (student != null && book != null) {
                            Issue issue = new Issue(issueDate, returnDate, student, book);
                            issue.setIssueId(id);
                            issues.add(issue);

                            if (id >= nextId) {
                                nextId = id + 1;
                            }
                        } else {
                            System.err.printf("⚠️ Skipping issue record with missing student/book: USN=%s, ISBN=%s%n", usn, isbn);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("⚠️ Skipping invalid issue ID line: " + Arrays.toString(parts));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // No issues yet — first time run
        } catch (IOException e) {
            throw new RuntimeException("Error loading issues", e);
        }

        return issues;
    }

    // Find by student USN
    public List<Issue> findByStudentUsn(String usn, Map<String, Student> studentMap, Map<String, Book> bookMap) {
        return findAll(studentMap, bookMap).stream()
                .filter(issue -> issue.getIssueStudent() != null &&
                        issue.getIssueStudent().getUsn().equalsIgnoreCase(usn))
                .toList();
    }

    // Validate an issue before saving
    private void validateIssue(Issue issue) {
        if (issue == null) {
            throw new IllegalArgumentException("❌ Issue cannot be null.");
        }
        if (issue.getIssueStudent() == null) {
            throw new IllegalArgumentException("❌ Issue must have a student.");
        }
        if (issue.getIssueBook() == null) {
            throw new IllegalArgumentException("❌ Issue must have a book.");
        }
        if (issue.getIssueDate() == null || issue.getIssueDate().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Issue date cannot be empty.");
        }
        if (issue.getReturnDate() == null || issue.getReturnDate().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Return date cannot be empty.");
        }
    }

    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
