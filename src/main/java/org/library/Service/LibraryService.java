package org.library.Service;

import org.library.model.*;
import org.library.repository.*;
import org.library.util.IsbnUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private final BookRepository bookRepository = new BookRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final StudentRepository studentRepository = new StudentRepository();
    private final IssueRepository issueRepository = new IssueRepository();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private int issueIdCounter = 1;

    // 1. Add book with author
    public void addBookWithAuthor(String isbn, String title, String category, int quantity, String authorName, String authorEmail) {
        // Create and save book
        Book book = new Book(isbn, title, category, quantity);
        bookRepository.save(book);

        // Load authors and check if email is already used
        Map<String, Book> bookMap = bookRepository.toMap();
        List<Author> authors = authorRepository.findAll(bookMap);

        boolean emailExists = authors.stream()
                .anyMatch(a -> a.getEmail().equalsIgnoreCase(authorEmail));

        if (emailExists) {
            throw new IllegalArgumentException("❌ Author with this email already exists: " + authorEmail);
        }

        // Save author if email is unique
        Author author = new Author(authorName, authorEmail, book);
        authorRepository.save(author, bookMap);
    }


    // 2. Search books by title
    public List<Book> searchBookByTitle(String title) {
        String normalized = title.trim().toLowerCase();
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle() != null && book.getTitle().trim().toLowerCase().equals(normalized))
                .collect(Collectors.toList());
    }

    // 3. Search books by category
    public List<Book> searchBookByCategory(String category) {
        String normalized = category.trim().toLowerCase();
        return bookRepository.findAll().stream()
                .filter(book -> book.getCategory() != null && book.getCategory().trim().toLowerCase().equals(normalized))
                .collect(Collectors.toList());
    }

    // 4. Search books by author
    public List<Author> searchBookByAuthor(String authorName) {
        String normalized = authorName.trim().toLowerCase();
        return authorRepository.findAll(bookRepository.toMap()).stream()
                .filter(author -> author.getName() != null && author.getName().trim().toLowerCase().equals(normalized))
                .collect(Collectors.toList());
    }

    // 5. List all books with authors
    public List<Author> listAllBooksWithAuthors() {
        return authorRepository.findAll(bookRepository.toMap());
    }

    // 6. Issue book to student
    public Issue issueBook(String usn, String studentName, String isbn) {
        if (usn == null || usn.trim().isEmpty() ||
                studentName == null || studentName.trim().isEmpty() ||
                isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("USN, student name, and ISBN must not be empty.");
        }

        // Normalize and validate ISBN
        String cleanIsbn = IsbnUtil.normalize(isbn);
        IsbnUtil.validate(cleanIsbn);

        Book book = bookRepository.findByIsbn(cleanIsbn);
        if (book == null || book.getQuantity() <= 0) {
            return null;  // Book not found or out of stock
        }

        Student student = studentRepository.findByUsn(usn.trim());
        if (student == null) {
            student = new Student(usn.trim(), studentName.trim());
            studentRepository.save(student);
        }

        String issueDate = LocalDate.now().format(DATE_FORMAT);
        String returnDate = LocalDate.now().plusDays(7).format(DATE_FORMAT);

        Issue issue = new Issue(issueDate, returnDate, student, book);
        issue.setIssueId(issueIdCounter++);
        issueRepository.save(issue);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.update(book);

        return issue;
    }

    // 7. List books issued by student USN
    public List<Issue> listBooksByUsn(String usn) {
        String normalized = usn.trim();
        return issueRepository.findAll(studentRepository.toMap(), bookRepository.toMap()).stream()
                .filter(issue -> issue.getIssueStudent() != null &&
                        issue.getIssueStudent().getUsn().equalsIgnoreCase(normalized))
                .collect(Collectors.toList());
    }
}
