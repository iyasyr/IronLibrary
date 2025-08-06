package org.library.repository;

import org.library.model.Author;
import org.library.model.Book;

import java.io.*;
import java.util.*;

public class AuthorRepository {

    private static final String FILE_PATH = "authors.csv";
    private static int nextId = 1;

    // Save a new author
    public void save(Author author) {
        author.setAuthorId(nextId++);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%d,%s,%s,%s",
                    author.getAuthorId(),
                    escape(author.getName()),
                    escape(author.getEmail()),
                    escape(author.getAuthorBook() != null ? author.getAuthorBook().getIsbn() : "")
            ));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving author", e);
        }
    }

    // Load all authors (requires map of ISBN -> Book for linking)
    public List<Author> findAll(Map<String, Book> bookMap) {
        List<Author> authors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = unescape(parts[1]);
                    String email = unescape(parts[2]);
                    String isbn = unescape(parts[3]);

                    Book linkedBook = bookMap.getOrDefault(isbn, null);

                    Author author = new Author(name, email, linkedBook);
                    author.setAuthorId(id);
                    authors.add(author);

                    // Keep ID generator updated
                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // First-time read, file doesn't exist — ignore
        } catch (IOException e) {
            throw new RuntimeException("Error loading authors", e);
        }
        return authors;
    }

    // Escape commas
    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    // Unescape commas
    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
