package org.library.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Issue {

    private int issueId;
    private String issueDate;
    private String returnDate;
    private Student issueStudent;
    private Book issueBook;

    public Issue(String issueDate, String returnDate, Student issueStudent, Book issueBook) {
        setIssueDate(issueDate);
        setReturnDate(returnDate);
        setIssueStudent(issueStudent);
        setIssueBook(issueBook);
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        if (issueId < 0) {
            throw new IllegalArgumentException("❌ Issue ID must be positive.");
        }
        this.issueId = issueId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        if (issueDate == null || issueDate.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Issue date cannot be empty.");
        }
        try {
            LocalDate.parse(issueDate); // Validates date format (ISO_LOCAL_DATE: yyyy-MM-dd)
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("❌ Invalid issue date format. Expected format: yyyy-MM-dd");
        }
        this.issueDate = issueDate.trim();
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        if (returnDate == null || returnDate.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Return date cannot be empty.");
        }
        try {
            LocalDate.parse(returnDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("❌ Invalid return date format. Expected format: yyyy-MM-dd");
        }
        this.returnDate = returnDate.trim();
    }

    public Student getIssueStudent() {
        return issueStudent;
    }

    public void setIssueStudent(Student issueStudent) {
        if (issueStudent == null) {
            throw new IllegalArgumentException("❌ Student cannot be null.");
        }
        // ensure valid fields
        issueStudent.setUsn(issueStudent.getUsn());
        issueStudent.setName(issueStudent.getName());

        this.issueStudent = issueStudent;
    }

    public Book getIssueBook() {
        return issueBook;
    }

    public void setIssueBook(Book issueBook) {
        if (issueBook == null) {
            throw new IllegalArgumentException("❌ Book cannot be null.");
        }
        this.issueBook = issueBook;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueId=" + issueId +
                ", issueDate='" + issueDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", issueStudent=" + issueStudent +
                ", issueBook=" + issueBook +
                '}';
    }
}
