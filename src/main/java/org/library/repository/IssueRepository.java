package org.library.repository;

import org.library.model.Book;
import org.library.model.Issue;
import org.library.model.Student;

import java.io.*;
import java.util.*;

public class IssueRepository {

    private static final String FILE_PATH = "issues.csv";
    private static int nextId = 1;

    // Guardar un issue
    public void save(Issue issue) {
        issue.setIssueId(nextId++);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%d,%s,%s,%s,%s",
                    issue.getIssueId(),
                    escape(issue.getIssueDate()),
                    escape(issue.getReturnDate()),
                    escape(issue.getIssueStudent() != null ? issue.getIssueStudent().getUsn() : ""),
                    escape(issue.getIssueBook() != null ? issue.getIssueBook().getIsbn() : "")
            ));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving issue", e);
        }
    }

    // Cargar todos los issues
    public List<Issue> findAll(Map<String, Student> studentMap, Map<String, Book> bookMap) {
        List<Issue> issues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String issueDate = unescape(parts[1]);
                    String returnDate = unescape(parts[2]);
                    String usn = unescape(parts[3]);
                    String isbn = unescape(parts[4]);

                    Student student = studentMap.get(usn);
                    Book book = bookMap.get(isbn);

                    Issue issue = new Issue(issueDate, returnDate, student, book);
                    issue.setIssueId(id);
                    issues.add(issue);

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // archivo aún no existe
        } catch (IOException e) {
            throw new RuntimeException("Error loading issues", e);
        }
        return issues;
    }

    // Buscar issues por USN
    public List<Issue> findByStudentUsn(String usn, Map<String, Student> studentMap, Map<String, Book> bookMap) {
        return findAll(studentMap, bookMap).stream()
                .filter(issue -> issue.getIssueStudent() != null &&
                        issue.getIssueStudent().getUsn().equalsIgnoreCase(usn))
                .toList();
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
