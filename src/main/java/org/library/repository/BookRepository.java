package org.library.repository;

import org.library.model.Book;
import org.library.util.IsbnUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookRepository {

    private static final String DEFAULT_FILE_PATH = "books.csv";
    private final String filePath;

    // Default constructor (production)
    public BookRepository() {
        this.filePath = DEFAULT_FILE_PATH;
    }

    // Constructor with custom file path (for testing)
    public BookRepository(String filePath) {
        this.filePath = filePath;
    }

    private void checkIsbnUniqueness(String isbn) {
        String normalized = IsbnUtil.normalize(isbn);
        boolean exists = findAll().stream()
                .map(book -> IsbnUtil.normalize(book.getIsbn()))
                .anyMatch(existingIsbn -> existingIsbn.equalsIgnoreCase(normalized));

        if (exists) {
            throw new IllegalArgumentException("ISBN already exists: " + isbn);
        }
    }

    // Save a book (append mode)
    public void save(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            IsbnUtil.validate(book.getIsbn());
            checkIsbnUniqueness(book.getIsbn());

            book.setIsbn(IsbnUtil.normalize(book.getIsbn()));
            writer.write(toCsvLine(book));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving book", e);
        }
    }

    // Return all books
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(fromCsvLine(line));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet — treat as empty list
        } catch (IOException e) {
            throw new RuntimeException("Error reading books", e);
        }
        return books;
    }

    // Find one book by ISBN
    public Book findByIsbn(String isbn) {
        return findAll().stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    // Update an existing book (by ISBN)
    public void update(Book updatedBook) {
        List<Book> books = findAll();
        List<Book> updatedList = books.stream()
                .map(b -> b.getIsbn().equalsIgnoreCase(updatedBook.getIsbn()) ? updatedBook : b)
                .toList();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : updatedList) {
                writer.write(toCsvLine(book));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating book", e);
        }
    }

    // Create a map of ISBN -> Book
    public Map<String, Book> toMap() {
        return findAll().stream()
                .collect(Collectors.toMap(Book::getIsbn, book -> book));
    }

    private String toCsvLine(Book book) {
        return String.join(",",
                escape(book.getIsbn()),
                escape(book.getTitle()),
                escape(book.getCategory()),
                String.valueOf(book.getQuantity()));
    }

    private Book fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        return new Book(
                unescape(parts[0]),
                unescape(parts[1]),
                unescape(parts[2]),
                Integer.parseInt(parts[3])
        );
    }

    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
