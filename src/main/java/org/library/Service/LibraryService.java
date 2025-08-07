package org.library.Service;

import org.library.model.*;
import org.library.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private final BookRepository bookRepository = new BookRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final StudentRepository studentRepository = new StudentRepository();
    private final IssueRepository issueRepository = new IssueRepository();

    private int issueIdCounter = 1;

    // 1. Add book with author
    public void addBookWithAuthor(String isbn, String title, String category, int quantity, String authorName, String authorEmail) {
        Book book = new Book(isbn, title, category, quantity);
        bookRepository.save(book);

        List<Author> authors = authorRepository.findAll(bookRepository.toMap());
        boolean exists = authors.stream().anyMatch(a ->
                a.getName().equalsIgnoreCase(authorName)
                        && a.getEmail().equalsIgnoreCase(authorEmail)
                        && a.getAuthorBook().getIsbn().equalsIgnoreCase(isbn)
        );

        if (!exists) {
            Author author = new Author(authorName, authorEmail, book);
            authorRepository.save(author);
        }
    }

    // 2. Search books by title
    public List<Book> searchBookByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    // 3. Search books by category
    public List<Book> searchBookByCategory(String category) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 4. Search books by author
    public List<Author> searchBookByAuthor(String authorName) {
        return authorRepository.findAll(bookRepository.toMap()).stream()
                .filter(author -> author.getName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    // 5. List all books with authors
    public List<Author> listAllBooksWithAuthors() {
        return authorRepository.findAll(bookRepository.toMap());
    }

    // 6. Issue book to student
    public String issueBook(String usn, String studentName, String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        if (book == null || book.getQuantity() <= 0) {
            return "Book not available";
        }

        Student student = studentRepository.findByUsn(usn);
        if (student == null) {
            student = new Student(usn, studentName);
            studentRepository.save(student);
        }

        LocalDateTime issueDate = LocalDateTime.now();
        LocalDateTime returnDate = issueDate.plusDays(7);

        Issue issue = new Issue();
        issue.setIssueId(issueIdCounter++);
        issue.setIssueDate(issueDate.toString());
        issue.setReturnDate(returnDate.toString());
        issue.setIssueStudent(student);
        issue.setIssueBook(book);

        issueRepository.save(issue);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.update(book);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Book issued. Return date: " + returnDate.format(formatter);
    }

    // 7. List books issued by student USN
    public List<Issue> listBooksByUsn(String usn) {
        return issueRepository.findAll(studentRepository.toMap(), bookRepository.toMap()).stream()
                .filter(issue -> issue.getIssueStudent().getUsn().equals(usn))
                .collect(Collectors.toList());
    }
}
